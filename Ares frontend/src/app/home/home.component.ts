import { Component } from '@angular/core';
import { PhotoService } from '../services/photo.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {
  constructor(public photoService: PhotoService) {}

  ngOnInit(): void {
    this.photoService.updateLatest()
  }
}
