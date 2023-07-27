import { Injectable } from '@angular/core';
import { Photo } from '../api/photo';

@Injectable({
  providedIn: 'root'
})
export class PhotoService {
  BACKEND_URL = "//localhost:8080/ares/api/";
  latestPhotos: {roverName: string, roverPhotos: Photo[]}[] = [];
  isLoading = false;
  lastUpdate = 0;

  constructor() { }

  updateLatest() : void {
    // Avoid new calls if data still fresh
    if(this.lastUpdate + 300000 < Date.now()) {
      this.lastUpdate = Date.now();
      this.isLoading = true;
      fetch(this.BACKEND_URL + "latest-photos")
      .then(response => response.json()) 
      .then(data => {
        this.latestPhotos = data;
        this.isLoading = false;
      }).catch(error => { 
        this.isLoading = false;
        console.log("Error", error);
      });
    }
  }

  toggleFavorite(photo: Photo) : void {
    console.log(photo);
    let requestOptions = {
      method: photo.favorite? "DELETE":"POST",
    };

    fetch(this.BACKEND_URL + "favorite/" + photo.id, requestOptions)
    .then(response => {
      photo.favorite = !photo.favorite;
    }).catch(error => { 
      this.isLoading = false;
      console.log("Error", error);
    });
  }

  getFavorites(): Promise<Photo[]> {
    this.isLoading = true;
    return fetch(this.BACKEND_URL + "user-favorites")
    .then(response =>  response.json())
    .then(data => {
      console.log(data);
      this.isLoading = false;
      return data;
    }) .catch(error => { 
      this.isLoading = false;
      console.log("Error", error);
    });
  }

  getSearchResults(searchParams: any): Promise<Photo[]> {
    this.isLoading = true;
    return fetch(this.BACKEND_URL + "search-photos?" + new URLSearchParams(searchParams))
    .then(response =>  response.json())
    .then(data => {
      this.isLoading = false;
      return data;
    }) .catch(error => { 
      this.isLoading = false;
      console.log("Error", error);
    });
  }
}
