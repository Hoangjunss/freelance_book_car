import { AfterViewInit, Component, OnInit, Type } from '@angular/core';
import { Router } from '@angular/router';
import { LocationDetailComponent } from '../location-detail/location-detail.component';
import { CarouselModule } from 'ngx-owl-carousel-o';
import { CommonModule } from '@angular/common';
import { LocationListComponent } from '../location-list/location-list.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CarouselModule, CommonModule, LocationListComponent, LocationDetailComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit, AfterViewInit {

  tabs: Array<{ label: string, icon: string, component: Type<any> }> = [];
  selectedTab: { label: string, icon: string, component: Type<any> } | null = null;
  locations: string[] = ['Đà Nẵng', 'Hội An', 'Bà Nà', 'Sân Bay', 'Nam Hội An'];
  selectedLocation: string | null = null;
  isDropdownVisible: { [key: string]: boolean } = {};

  // Ảnh quảng cáo
  images = [
    { src: 'assets/img/TRANGBIA/CAR_RENTAL.jpg', alt: 'Car', info: 'Thông tin về bức ảnh 1' },
    { src: 'assets/img/TRANGBIA/hotel.jpg', alt: 'Hotels', info: 'Thông tin về bức ảnh 2' },
    { src: 'assets/img/TRANGBIA/rungdua.png', alt: 'Attractions', info: 'Thông tin về bức ảnh 3' }
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

     // Nếu tab đã được chọn, đảo ngược trạng thái hiển thị menu
     if (this.isDropdownVisible[tab.label]) {
      this.isDropdownVisible[tab.label] = !this.isDropdownVisible[tab.label];
    } else {
      // Nếu không, đặt menu cho tab này thành visible và các tab khác thành false
      this.isDropdownVisible = { [tab.label]: true };
    }
  }

  selectLocation(location: string): void {
    this.selectedLocation = location;
    // Điều hướng đến đúng đường dẫn dựa trên tab hiện tại
    if (this.selectedTab) {
      const tabLabel = this.selectedTab.label;
      let path = '';

      if (tabLabel === 'Thuê Xe') {
        path = 'location-list';
      } else if (tabLabel === 'Thuê Khách Sạn') {
        path = 'hotel-list';
      } else if (tabLabel === 'Đặt Vé') {
        path = 'ticket-list';
      }

      this.router.navigate([path, encodeURIComponent(this.selectedLocation)]);
      console.log(this.selectedLocation);
      console.log(`Navigating to: /${path}/${encodeURIComponent(this.selectedLocation)}`);
    }

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

      document.querySelectorAll('.fadeInRight, .fadeInLeft, .fadeInUp,.slideInFromRight,.fadeInWrapper').forEach(item => {
        observer.observe(item);
      });
    }
    else {
      console.warn('IntersectionObserver không được hỗ trợ trong môi trường này.');
    }
  }

}
