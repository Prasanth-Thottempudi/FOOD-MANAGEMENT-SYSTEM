import { AfterViewInit, Component } from '@angular/core';
import Map from 'ol/Map';
import View from 'ol/View';
import TileLayer from 'ol/layer/Tile';
import OSM from 'ol/source/OSM';
import { fromLonLat, toLonLat } from 'ol/proj';
import Feature from 'ol/Feature';
import Point from 'ol/geom/Point';
import VectorSource from 'ol/source/Vector';
import VectorLayer from 'ol/layer/Vector';
import { Icon, Style, Stroke } from 'ol/style';
import LineString from 'ol/geom/LineString';

@Component({
  selector: 'app-tracking',
  templateUrl: './tracking.component.html',
  styleUrls: ['./tracking.component.css'],
})
export class TrackingComponent implements AfterViewInit {
  map!: Map;
  vectorSource!: VectorSource;
  routeLayer!: VectorLayer;

  startCoords: [number, number] = [78.3773, 17.4426]; // Hyderabad HiTech City [lng, lat]
  userCoords: [number, number] | null = null;

  etaMinutes: number | null = null;

  bikeFeature!: Feature;
  routeFeature!: Feature;

  reached: boolean = false;
  loadingRoute: boolean = false;

  customerPhone = '+919876543210';

  ngAfterViewInit() {
    this.initMap();
    this.getUserLocationAndRoute();
  }

  private initMap() {
    this.vectorSource = new VectorSource();

    this.routeLayer = new VectorLayer({
      source: this.vectorSource,
      style: new Style({
        stroke: new Stroke({
          color: '#007bff',
          width: 5,
          lineCap: 'round',
          lineJoin: 'round',
        }),
      }),
    });

    this.map = new Map({
      target: 'map',
      layers: [new TileLayer({ source: new OSM() }), this.routeLayer],
      view: new View({
        center: fromLonLat(this.startCoords),
        zoom: 13,
      }),
    });

    // Bike marker
    this.bikeFeature = new Feature({
      geometry: new Point(fromLonLat(this.startCoords)),
    });
    this.bikeFeature.setStyle(
      new Style({
        image: new Icon({
          src: 'https://twemoji.maxcdn.com/v/latest/72x72/1f6f5.png',
          scale: 1,
          anchor: [0.5, 1],
        }),
      })
    );
    this.vectorSource.addFeature(this.bikeFeature);
  }

  private getUserLocationAndRoute() {
    if (!navigator.geolocation) {
      alert('Geolocation is not supported by your browser');
      return;
    }

    this.loadingRoute = true; // show loading spinner

    navigator.geolocation.getCurrentPosition(
      (position) => {
        this.userCoords = [position.coords.longitude, position.coords.latitude];

        // User marker (blue dot)
        const userFeature = new Feature({
          geometry: new Point(fromLonLat(this.userCoords)),
        });
        userFeature.setStyle(
          new Style({
            image: new Icon({
              src: 'https://maps.google.com/mapfiles/ms/icons/blue-dot.png',
              scale: 1,
              anchor: [0.5, 1],
            }),
          })
        );
        this.vectorSource.addFeature(userFeature);

        // Center map between start and user
        const centerLng = (this.startCoords[0] + this.userCoords[0]) / 2;
        const centerLat = (this.startCoords[1] + this.userCoords[1]) / 2;
        this.map.getView().setCenter(fromLonLat([centerLng, centerLat]));
        this.map.getView().setZoom(13);

        this.getRouteFromOSRM(this.startCoords, this.userCoords);
      },
      () => {
        this.loadingRoute = false;
        alert('Unable to retrieve your location');
      }
    );
  }

  private getRouteFromOSRM(start: [number, number], end: [number, number]) {
    const url = `https://router.project-osrm.org/route/v1/bicycle/${start[0]},${start[1]};${end[0]},${end[1]}?overview=full&geometries=geojson`;

    fetch(url)
      .then((res) => res.json())
      .then((data) => {
        if (data.code !== 'Ok') {
          this.loadingRoute = true; // keep showing waiting
          return;
        }

        const route = data.routes[0];
        this.etaMinutes = Math.round(route.duration / 60);

        const routeCoords = route.geometry.coordinates.map(
          (coord: [number, number]) => fromLonLat(coord)
        );

        if (this.routeFeature) {
          this.vectorSource.removeFeature(this.routeFeature);
        }

        this.routeFeature = new Feature({
          geometry: new LineString(routeCoords),
        });
        this.vectorSource.addFeature(this.routeFeature);

        this.animateBike(routeCoords, route.duration);
        this.loadingRoute = false; // hide loading popup
      })
      .catch(() => {
        this.loadingRoute = true; // show waiting popup if route fetch fails
      });
  }

  private animateBike(routeCoords: number[][], durationSeconds: number) {
    let index = 0;
    const stepTime = 1000; // 1 second per step approx
    const totalSteps = routeCoords.length;

    const moveStep = () => {
      if (index >= totalSteps) {
        this.reached = true;
        return;
      }

      // Move bike to next coord
      (this.bikeFeature.getGeometry() as Point).setCoordinates(
        routeCoords[index]
      );

      // Update route line to show remaining path only
      const remainingCoords = routeCoords.slice(index);
      (this.routeFeature.getGeometry() as LineString).setCoordinates(
        remainingCoords
      );

      // Check distance to user
      if (this.userCoords) {
        const bikeLonLat = toLonLat(routeCoords[index]);
        const dist = this.calculateDistance(
          bikeLonLat[1],
          bikeLonLat[0],
          this.userCoords[1],
          this.userCoords[0]
        );
        if (dist < 0.02) {
          this.reached = true;
          return;
        }
      }

      // Update ETA
      const remainingSteps = totalSteps - index;
      const timePerStep = durationSeconds / totalSteps;
      this.etaMinutes = Math.ceil((remainingSteps * timePerStep) / 60);

      index++;
      setTimeout(moveStep, stepTime);
    };

    moveStep();
  }

  private calculateDistance(
    lat1: number,
    lon1: number,
    lat2: number,
    lon2: number
  ): number {
    const R = 6371; // Earth radius in km
    const dLat = this.deg2rad(lat2 - lat1);
    const dLon = this.deg2rad(lon2 - lon1);
    const a =
      Math.sin(dLat / 2) * Math.sin(dLat / 2) +
      Math.cos(this.deg2rad(lat1)) *
        Math.cos(this.deg2rad(lat2)) *
        Math.sin(dLon / 2) *
        Math.sin(dLon / 2);
    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return R * c;
  }

  private deg2rad(deg: number): number {
    return deg * (Math.PI / 180);
  }

  callCustomer() {
    window.open(`tel:${this.customerPhone}`, '_self');
  }

  messageCustomer() {
    window.open(`sms:${this.customerPhone}`, '_self');
  }
}
