export interface UserRequest {
  name: string;
  email: string;
  password: string;
  mobileNumber: string;
  profileImage: File | null; // <-- allow File
  latitude?: number | null;
  longitude?: number | null;
  addressLine1?: string;
  addressLine2?: string;
  city?: string;
  state?: string;
  postalCode?: string;
  country?: string;
}
