import { CommonModule } from '@angular/common';
import { Component, OnInit, Type, AfterViewInit } from '@angular/core';
import { CarouselModule } from 'ngx-owl-carousel-o';
import { LocationListComponent } from '../location-list/location-list.component';
import { LocationDetailComponent } from '../location-detail/location-detail.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CarouselModule, CommonModule, LocationListComponent, LocationDetailComponent],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit, AfterViewInit {
  tabs: Array<{ label: string, icon: string, component: Type<any> }> = [];
  selectedTab: { label: string, icon: string, component: Type<any> } | null = null;
  locations: string[] = ['Đà Nẵng', 'Hội An', 'Bà Nà', 'Sân Bay', 'Nam Hội An'];
  selectedLocation: string | null = null;

  // image
  images = [
    { src: 'https://img.tripi.vn/cdn-cgi/image/width=700,height=700/https://gcs.tripi.vn/public-tripi/tripi-feed/img/474111PXL/anh-dep-nhat-the-gioi-ve-thien-nhien_041753462.jpg', alt: 'Image 1', info: 'Thông tin về bức ảnh 1' },
    { src: 'https://img.tripi.vn/cdn-cgi/image/width=700,height=700/https://gcs.tripi.vn/public-tripi/tripi-feed/img/474111PXL/anh-dep-nhat-the-gioi-ve-thien-nhien_041753462.jpg', alt: 'Image 2', info: 'Thông tin về bức ảnh 2' },
    { src: 'https://img.tripi.vn/cdn-cgi/image/width=700,height=700/https://gcs.tripi.vn/public-tripi/tripi-feed/img/474111PXL/anh-dep-nhat-the-gioi-ve-thien-nhien_041753462.jpg', alt: 'Image 3', info: 'Thông tin về bức ảnh 3' }
  ];


  constructor(private router: Router) { }

  ngAfterViewInit(): void {
    this.initIntersectionObserver();
  }
  ngOnInit(): void {
    this.tabs = [
      { label: 'Thuê Xe', icon: 'fa-solid fa-car', component: LocationListComponent },
      { label: 'Thuê Khách Sạn', icon: 'fa-solid fa-hotel', component: LocationListComponent },
      { label: 'Đặt Vé', icon: 'fa-solid fa-ticket', component: LocationDetailComponent }
    ];
    this.selectedTab = this.tabs.find(tab => tab.label === 'Đặt Vé') || this.tabs[0]; // Chọn 'Đặt Vé' mặc định
  }

  selectTab(tab: { label: string, icon: string, component: Type<any> }): void {
    this.selectedTab = tab;
  }

  selectLocation(location: string): void {
    this.selectedLocation = location;
    this.router.navigate(['/location-list', encodeURIComponent(this.selectedLocation)]);
  }

  initIntersectionObserver(): void {
    if (typeof IntersectionObserver !== 'undefined') {
      const options = {
        root: null,
        rootMargin: '0px',
        threshold: 0.1
      };

      const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
          if (entry.isIntersecting) {
            entry.target.classList.add('visible');
          } else {
            entry.target.classList.remove('visible');
          }
        });
      }, options);

      document.querySelectorAll('.fadeInRight, .fadeInLeft, .fadeInUp,.image-item,.image-item p').forEach(item => {
        observer.observe(item);
      });
    }
    else {
      console.warn('IntersectionObserver không được hỗ trợ trong môi trường này.');
    }
  }


}