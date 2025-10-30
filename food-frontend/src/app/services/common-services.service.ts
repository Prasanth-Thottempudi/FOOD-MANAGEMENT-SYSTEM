import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CommonServicesService {
  constructor(private http: HttpClient) {}

  userLogin(payload: any): Observable<any> {
    return this.http.post('http://localhost:9091/user/auth/login', payload);
  }

  adminServiceApprovalRequests(): Observable<any> {
    return this.http.get(
      'http://localhost:9092/services/business/approval-requested-services'
    );
  }

  getAllServices(): Observable<any> {
    return this.http.get('http://localhost:9092/services/get-all-services');
  }

  businessApprovalRequest(payload: any): Observable<any> {
    return this.http.put(
      'http://localhost:9092/services/business/business-approval-request',
      payload
    );
  }

  getAllRecipesByBusinessId(businessId: string): Observable<any> {
    return this.http.get(`http://localhost:9093/food-delivery/get-all-recipes/${businessId}`);
  }

  getAllRecipes():Observable<any>{
    return this.http.get('http://localhost:9093/food-delivery/get-all-recipes')
  }
  
}
