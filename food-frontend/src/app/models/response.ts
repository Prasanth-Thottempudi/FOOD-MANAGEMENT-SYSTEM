export class Response {
  responseMessage: string;
  responseStatus: string;

  constructor(responseMessage?: string, responseStatus?: string) {
    this.responseMessage = responseMessage;
    this.responseStatus = responseStatus;
  }
}
