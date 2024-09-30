import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NavbarComponent } from "../../../user/navbar/navbar.component";
import { HomeComponent } from "../../../user/home/home.component";
import { GetPageResponse } from '../../../../models/response/home/get-page-response';

@Component({
  selector: 'app-page-home',
  standalone: true,
  imports: [CommonModule, FormsModule, NavbarComponent, HomeComponent],
  templateUrl: './page-home.component.html',
  styleUrl: './page-home.component.css'
})
export class PageHomeComponent implements OnInit{
  defaultVideoUrl: string = 'https://hoianmemoriesland.com/public/media/tvc/tvc0808.mp4';

  homeData: GetPageResponse = new GetPageResponse();
  detailData: GetPageResponse [] = [];
  footerData: GetPageResponse = new GetPageResponse();

  isDisplayDetails: boolean = false;

  ngOnInit(): void {
    
  }

  editVideo() {
    const videoUrlInput = document.getElementById('videoUrl') as HTMLInputElement;
    const newVideoUrl = videoUrlInput.value;

    if (newVideoUrl) {
      const videoElement = document.getElementById('videoElement') as HTMLVideoElement;
      videoElement.src = newVideoUrl; // Cập nhật đường dẫn video mới
      videoElement.load(); // Tải video mới
      videoUrlInput.value = ''; // Xóa ô nhập
    } else {
      alert('Please enter a valid video URL');
    }
  }

  openPageHome(){
    this.isDisplayDetails = true;
  }

  closePageHome(){
    this.isDisplayDetails = false;
  }

}
