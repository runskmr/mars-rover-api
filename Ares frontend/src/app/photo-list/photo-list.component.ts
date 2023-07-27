import { Component, Input } from '@angular/core';
import { PhotoService } from '../services/photo.service';
import { Photo } from '../api/photo';

@Component({
  selector: 'app-photo-list',
  templateUrl: './photo-list.component.html',
  styleUrls: ['./photo-list.component.scss']
})
export class PhotoListComponent {
  @Input() photos: Photo[] = [];
  
  constructor(public photoService: PhotoService) {}
}
