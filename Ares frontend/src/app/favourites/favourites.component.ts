import { Component } from '@angular/core';
import { PhotoService } from '../services/photo.service';
import { Photo } from '../api/photo';

@Component({
  selector: 'app-favourites',
  templateUrl: './favourites.component.html',
  styleUrls: ['./favourites.component.scss']
})
export class FavouritesComponent {

  favoriteResults: Photo[] = [];

  constructor(public photoService: PhotoService) {}

  ngOnInit(): void {
    this.getFavorites()
  }
  
  async getFavorites(): Promise<void> {
    this.favoriteResults = await this.photoService.getFavorites();
  }
}
