import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators, ValidatorFn, AbstractControl, ValidationErrors } from '@angular/forms';
import { PhotoService } from '../services/photo.service';
import { Photo } from '../api/photo';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent {
  MILLISECONDS_PER_DAY = 86400000;
  ROVERS = ["Perseverance", "Curiosity", "Opportunity", "Spirit"];
  CAMERAS = {
    //Perseverance
    EDL_RUCAM: "Rover Up-Look Camera",
    EDL_RDCAM: "Rover Down-Look Camera",
    EDL_DDCAM: "Descent Stage Down-Look Camera",
    EDL_PUCAM1: "Parachute Up-Look Camera A",
    EDL_PUCAM2: "Parachute Up-Look Camera B",
    NAVCAM_LEFT: "Left Navigation Camera",
    NAVCAM_RIGHT: "Right Navigation Camera",
    MCZ_RIGHT: "Right Mast Camera Zoom",
    MCZ_LEFT: "Left Mast Camera Zoom",
    FRONT_HAZCAM_LEFT_A: "Left Front Hazard Avoidance Camera",
    FRONT_HAZCAM_RIGHT_A: "Right Front Hazard Avoidance Camera",
    REAR_HAZCAM_LEFT: "Left Rear Hazard Avoidance Camera",
    REAR_HAZCAM_RIGHT: "Right Rear Hazard Avoidance Camera",
    SKYCAM: "MEDA Skycam",
    SHERLOC_WATSON: "SHERLOC WATSON Camera",
    //Others
    FHAZ: "Front Hazard Avoidance Camera",
    RHAZ: "Rear Hazard Avoidance Camera",
    MAST: "Mast Camera",
    CHEMCAM: "Chemistry and Camera Complex",
    MAHLI: "Mars Hand Lens Imager",
    MARDI: "Mars Descent Imager",
    NAVCAM: "Navigation Camera",
    PANCAM: "Panoramic Camera",
    MINITES: "Thermal Emission Spectrometer"
  }
  FIRST_DATE = new Date("2004-01-05");
  today = new Date();

  searchForm = new FormGroup({
    rovers: new FormControl(this.ROVERS, [Validators.required]),
    cameras: new FormControl(Object.keys(this.CAMERAS), [Validators.required]),
    startDate: new FormControl(new Date(), [Validators.required]),
    endDate: new FormControl(new Date(), [Validators.required]),
    favorite: new FormControl("all", [Validators.required]),
  });

  searchResults: Photo[] = [];

  constructor(public photoService: PhotoService) {
    this.searchForm.controls.endDate.addValidators(this.dateRangeValidator(7));
    this.searchForm.controls.startDate.valueChanges.subscribe(() => this.searchForm.controls.endDate.updateValueAndValidity());
  }
  
  async search(): Promise<void> {
    let searchParams = Object.fromEntries(Object.entries(this.searchForm.controls).map(([k, v]) => [k, v.value]))
    searchParams.startDate = (searchParams.startDate as Date).toLocaleDateString();
    searchParams.endDate = (searchParams.endDate as Date).toLocaleDateString();
    this.searchResults = await this.photoService.getSearchResults(searchParams);
  }

  //Prevent spamming third party API
  dateRangeValidator(maxRange: number): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const startDate = this.searchForm.controls.startDate.value;
      if(control.value && startDate) {
        return (control.value.getTime() - startDate.getTime())>(maxRange*this.MILLISECONDS_PER_DAY) ? {invalidRange: true} : null;
      } else {
        return null;
      }
    };
  }

  selectAll(isRovers: boolean): void {
    isRovers? this.searchForm.controls.rovers.setValue(this.ROVERS):this.searchForm.controls.cameras.setValue(Object.keys(this.CAMERAS));
  }

  deselectAll(isRovers: boolean): void {
    isRovers? this.searchForm.controls.rovers.setValue([]):this.searchForm.controls.cameras.setValue([]);
  }
}
