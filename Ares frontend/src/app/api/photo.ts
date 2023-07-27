export interface Photo {
	id: number;
	imgSrc: string;
	camera: string;
	date: string;
	rover: string;
	favorite: boolean;
	category?: string;
}
