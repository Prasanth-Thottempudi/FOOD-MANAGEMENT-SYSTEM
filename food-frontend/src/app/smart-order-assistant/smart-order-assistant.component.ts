import { Component, OnInit, NgZone } from '@angular/core';

interface Restaurant {
  name: string;
  rating: number;
  address: string;
}

@Component({
  selector: 'app-smart-order-assistant',
  templateUrl: './smart-order-assistant.component.html',
  styleUrls: ['./smart-order-assistant.component.css'],
})
export class SmartOrderAssistantComponent implements OnInit {
  userInput: string = '';
  language: string = 'en'; // Default language
  assistantMessages: string[] = [];
  nearbyRestaurants: Restaurant[] = [];
  orderPlaced: boolean = false;
  voices: SpeechSynthesisVoice[] = [];

  recognition: any;
  isListening: boolean = false;

  constructor(private ngZone: NgZone) {}

  ngOnInit() {
    this.loadVoices();
    if ('speechSynthesis' in window) {
      window.speechSynthesis.onvoiceschanged = () => this.loadVoices();
    }

    // Initialize Speech Recognition
    const { webkitSpeechRecognition }: any = window as any;
    if ('SpeechRecognition' in window || webkitSpeechRecognition) {
      const { webkitSpeechRecognition }: any = window as any;

      this.recognition = new ((window as any).SpeechRecognition ||
        webkitSpeechRecognition)();

      this.recognition.continuous = false;
      this.recognition.lang = 'en-US';
      this.recognition.interimResults = false;

      this.recognition.onresult = (event: any) => {
        const transcript = event.results[0][0].transcript;
        this.ngZone.run(() => {
          this.userInput = transcript;
          this.handleUserInput();
          this.isListening = false;
        });
      };

      this.recognition.onerror = (event: any) => {
        console.error(event);
        this.isListening = false;
      };
    }
  }

  loadVoices() {
    this.voices = window.speechSynthesis.getVoices();
  }

  speakText(text: string) {
    if ('speechSynthesis' in window) {
      const utterance = new SpeechSynthesisUtterance(text);

      switch (this.language) {
        case 'hi':
          utterance.lang = 'hi-IN';
          break;
        case 'fr':
          utterance.lang = 'fr-FR';
          break;
        case 'es':
          utterance.lang = 'es-ES';
          break;
        case 'te':
          utterance.lang = 'te-IN';
          break;
        default:
          utterance.lang = 'en-US';
      }

      const voice = this.voices.find((v) => v.lang === utterance.lang);
      if (voice) utterance.voice = voice;

      utterance.rate = 1;
      utterance.pitch = 1;
      window.speechSynthesis.speak(utterance);
    }
  }

  handleUserInput() {
    const input = this.userInput.trim().toLowerCase();
    if (!input) return;

    this.assistantMessages.push(`You: ${this.userInput}`);

    if (
      input.includes('biryani') ||
      input.includes('pizza') ||
      input.includes('burger')
    ) {
      this.suggestRestaurants(input);
    } else if (input.includes('go ahead') || input.includes('order')) {
      this.placeOrder();
    } else {
      this.assistantResponse(`Sorry, I did not understand. Please try again.`);
    }

    this.userInput = '';
  }

  assistantResponse(text: string) {
    this.assistantMessages.push(`Assistant: ${text}`);
    this.speakText(text);
  }

  suggestRestaurants(food: string) {
    this.nearbyRestaurants = [
      { name: 'Spicy Biryani House', rating: 4.5, address: '123 Main St' },
      { name: 'Royal Biryani', rating: 4.2, address: '456 Market Rd' },
      { name: 'Biryani King', rating: 4.7, address: '789 Food Lane' },
    ];

    const restaurantNames = this.nearbyRestaurants
      .map((r) => `${r.name} (${r.rating}⭐)`)
      .join(', ');
    this.assistantResponse(
      `I found these restaurants for ${food}: ${restaurantNames}. Say "go ahead" to place the order with Cash on Delivery.`
    );
  }

  placeOrder() {
    if (this.orderPlaced) {
      this.assistantResponse('Your order has already been placed.');
      return;
    }

    this.orderPlaced = true;
    this.assistantResponse(
      `Your order has been placed successfully with Cash on Delivery. Enjoy your meal!`
    );
  }

  changeLanguage(lang: string) {
    this.language = lang;
    this.assistantResponse(
      `Language changed to ${lang.toUpperCase()}. Hi! Welcome to NexGen Hub.`
    );
  }

  startAssistant() {
    this.assistantResponse(
      `Hi! Welcome to NexGen Hub. How can I help you today?`
    );
  }

  toggleListening() {
    if (!this.recognition) return;

    if (!this.isListening) {
      this.isListening = true;
      this.recognition.lang =
        this.language === 'hi'
          ? 'hi-IN'
          : this.language === 'fr'
          ? 'fr-FR'
          : this.language === 'es'
          ? 'es-ES'
          : this.language === 'te'
          ? 'te-IN'
          : 'en-US';
      this.recognition.start();
    } else {
      this.recognition.stop();
      this.isListening = false;
    }
  }
}
