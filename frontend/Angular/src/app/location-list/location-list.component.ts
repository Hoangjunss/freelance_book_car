import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-location-list',
  imports:[CommonModule,RouterLink],
  standalone: true,
  templateUrl: './location-list.component.html',
  styleUrls: ['./location-list.component.css']
})
export class LocationListComponent implements OnInit {
  location: string | null = null;
  locations: { name: string, imageUrl: string }[] = [];

  constructor(private route: ActivatedRoute, private router:Router) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.location = decodeURIComponent(params.get('location') || '');
      if (this.location) {
        this.locations = this.getLocationsForSelectedLocation(this.location);
      }
    });
  }

  getLocationsForSelectedLocation(location: string) {
    const allLocations = {
      'Đà Nẵng': [
        { name: 'Bãi biển Mỹ Khê', imageUrl: 'https://example.com/mykhe.jpg' },
        { name: 'Ngũ Hành Sơn', imageUrl: 'https://hoanghamobile.com/tin-tuc/wp-content/uploads/2023/07/anh-phong-canh-dep-41.jpg' },
        { name: 'Ngũ Hành Sơn', imageUrl: 'https://hoanghamobile.com/tin-tuc/wp-content/uploads/2023/07/anh-phong-canh-dep-41.jpg' },
        { name: 'Ngũ Hành Sơn', imageUrl: 'https://hoanghamobile.com/tin-tuc/wp-content/uploads/2023/07/anh-phong-canh-dep-41.jpg' },
        { name: 'Ngũ Hành Sơn', imageUrl: 'https://hoanghamobile.com/tin-tuc/wp-content/uploads/2023/07/anh-phong-canh-dep-41.jpg' },
        { name: 'Ngũ Hành Sơn', imageUrl: 'https://hoanghamobile.com/tin-tuc/wp-content/uploads/2023/07/anh-phong-canh-dep-41.jpg' },
        { name: 'Ngũ Hành Sơn', imageUrl: 'https://hoanghamobile.com/tin-tuc/wp-content/uploads/2023/07/anh-phong-canh-dep-41.jpg' },
        { name: 'Ngũ Hành Sơn', imageUrl: 'https://hoanghamobile.com/tin-tuc/wp-content/uploads/2023/07/anh-phong-canh-dep-41.jpg' },
        { name: 'Ngũ Hành Sơn', imageUrl: 'https://hoanghamobile.com/tin-tuc/wp-content/uploads/2023/07/anh-phong-canh-dep-41.jpg' },
        { name: 'Ngũ Hành Sơn', imageUrl: 'https://hoanghamobile.com/tin-tuc/wp-content/uploads/2023/07/anh-phong-canh-dep-41.jpg' },
        { name: 'Ngũ Hành Sơn', imageUrl: 'https://hoanghamobile.com/tin-tuc/wp-content/uploads/2023/07/anh-phong-canh-dep-41.jpg' },
        { name: 'Ngũ Hành Sơn', imageUrl: 'https://hoanghamobile.com/tin-tuc/wp-content/uploads/2023/07/anh-phong-canh-dep-41.jpg' },
        { name: 'Ngũ Hành Sơn', imageUrl: 'https://hoanghamobile.com/tin-tuc/wp-content/uploads/2023/07/anh-phong-canh-dep-41.jpg' },
      ],
      'Hội An': [
        { name: 'Phố cổ Hội An', imageUrl: 'https://example.com/phoco.jpg' },
        { name: 'Cầu Nhật Bản', imageUrl: 'https://hoanghamobile.com/tin-tuc/wp-content/uploads/2023/07/anh-phong-canh-dep-41.jpg' },
        { name: 'Cầu Nhật Bản', imageUrl: 'https://hoanghamobile.com/tin-tuc/wp-content/uploads/2023/07/anh-phong-canh-dep-41.jpg' },
        { name: 'Cầu Nhật Bản', imageUrl: 'https://hoanghamobile.com/tin-tuc/wp-content/uploads/2023/07/anh-phong-canh-dep-41.jpg' },
        { name: 'Cầu Nhật Bản', imageUrl: 'https://hoanghamobile.com/tin-tuc/wp-content/uploads/2023/07/anh-phong-canh-dep-41.jpg' },
        { name: 'Cầu Nhật Bản', imageUrl: 'https://hoanghamobile.com/tin-tuc/wp-content/uploads/2023/07/anh-phong-canh-dep-41.jpg' },
        { name: 'Cầu Nhật Bản', imageUrl: 'https://hoanghamobile.com/tin-tuc/wp-content/uploads/2023/07/anh-phong-canh-dep-41.jpg' },
        { name: 'Cầu Nhật Bản', imageUrl: 'https://hoanghamobile.com/tin-tuc/wp-content/uploads/2023/07/anh-phong-canh-dep-41.jpg' },
        { name: 'Cầu Nhật Bản', imageUrl: 'https://hoanghamobile.com/tin-tuc/wp-content/uploads/2023/07/anh-phong-canh-dep-41.jpg' },
      ],
      'Bà Nà': [
        { name: 'Cầu Vàng', imageUrl: 'https://example.com/cauvong.jpg' },
        { name: 'Khu du lịch Bà Nà', imageUrl: 'https://hoanghamobile.com/tin-tuc/wp-content/uploads/2023/07/anh-phong-canh-dep-41.jpg' },
        { name: 'Khu du lịch Bà Nà', imageUrl: 'https://hoanghamobile.com/tin-tuc/wp-content/uploads/2023/07/anh-phong-canh-dep-41.jpg' },
        { name: 'Khu du lịch Bà Nà', imageUrl: 'https://hoanghamobile.com/tin-tuc/wp-content/uploads/2023/07/anh-phong-canh-dep-41.jpg' },
        { name: 'Khu du lịch Bà Nà', imageUrl: 'https://hoanghamobile.com/tin-tuc/wp-content/uploads/2023/07/anh-phong-canh-dep-41.jpg' },
        { name: 'Khu du lịch Bà Nà', imageUrl: 'https://hoanghamobile.com/tin-tuc/wp-content/uploads/2023/07/anh-phong-canh-dep-41.jpg' },
        { name: 'Khu du lịch Bà Nà', imageUrl: 'https://hoanghamobile.com/tin-tuc/wp-content/uploads/2023/07/anh-phong-canh-dep-41.jpg' },
        { name: 'Khu du lịch Bà Nà', imageUrl: 'https://hoanghamobile.com/tin-tuc/wp-content/uploads/2023/07/anh-phong-canh-dep-41.jpg' },
        { name: 'Khu du lịch Bà Nà', imageUrl: 'https://hoanghamobile.com/tin-tuc/wp-content/uploads/2023/07/anh-phong-canh-dep-41.jpg' },
      ],
      'Sân Bay': [
        { name: 'Sân bay Đà Nẵng', imageUrl: 'https://hoanghamobile.com/tin-tuc/wp-content/uploads/2023/07/anh-phong-canh-dep-41.jpg' },
        { name: 'Sân bay Đà Nẵng', imageUrl: 'https://hoanghamobile.com/tin-tuc/wp-content/uploads/2023/07/anh-phong-canh-dep-41.jpg' },
        { name: 'Sân bay Đà Nẵng', imageUrl: 'https://hoanghamobile.com/tin-tuc/wp-content/uploads/2023/07/anh-phong-canh-dep-41.jpg' },
        { name: 'Sân bay Đà Nẵng', imageUrl: 'https://hoanghamobile.com/tin-tuc/wp-content/uploads/2023/07/anh-phong-canh-dep-41.jpg' },
        { name: 'Sân bay Đà Nẵng', imageUrl: 'https://hoanghamobile.com/tin-tuc/wp-content/uploads/2023/07/anh-phong-canh-dep-41.jpg' },
        { name: 'Sân bay Đà Nẵng', imageUrl: 'https://hoanghamobile.com/tin-tuc/wp-content/uploads/2023/07/anh-phong-canh-dep-41.jpg' },
        { name: 'Sân bay Đà Nẵng', imageUrl: 'https://hoanghamobile.com/tin-tuc/wp-content/uploads/2023/07/anh-phong-canh-dep-41.jpg' },
        { name: 'Sân bay Đà Nẵng', imageUrl: 'https://hoanghamobile.com/tin-tuc/wp-content/uploads/2023/07/anh-phong-canh-dep-41.jpg' },
      ],
      'Nam Hội An': [
        { name: 'Khu du lịch Nam Hội An', imageUrl: 'https://hoanghamobile.com/tin-tuc/wp-content/uploads/2023/07/anh-phong-canh-dep-41.jpg' },
        { name: 'Khu du lịch Nam Hội An', imageUrl: 'https://hoanghamobile.com/tin-tuc/wp-content/uploads/2023/07/anh-phong-canh-dep-41.jpg' },
        { name: 'Khu du lịch Nam Hội An', imageUrl: 'https://hoanghamobile.com/tin-tuc/wp-content/uploads/2023/07/anh-phong-canh-dep-41.jpg' },
        { name: 'Khu du lịch Nam Hội An', imageUrl: 'https://hoanghamobile.com/tin-tuc/wp-content/uploads/2023/07/anh-phong-canh-dep-41.jpg' },
        { name: 'Khu du lịch Nam Hội An', imageUrl: 'https://hoanghamobile.com/tin-tuc/wp-content/uploads/2023/07/anh-phong-canh-dep-41.jpg' },
        { name: 'Khu du lịch Nam Hội An', imageUrl: 'https://hoanghamobile.com/tin-tuc/wp-content/uploads/2023/07/anh-phong-canh-dep-41.jpg' },
        { name: 'Khu du lịch Nam Hội An', imageUrl: 'https://hoanghamobile.com/tin-tuc/wp-content/uploads/2023/07/anh-phong-canh-dep-41.jpg' },
        { name: 'Khu du lịch Nam Hội An', imageUrl: 'https://hoanghamobile.com/tin-tuc/wp-content/uploads/2023/07/anh-phong-canh-dep-41.jpg' },
        { name: 'Khu du lịch Nam Hội An', imageUrl: 'https://hoanghamobile.com/tin-tuc/wp-content/uploads/2023/07/anh-phong-canh-dep-41.jpg' },
      ]
    };
    return allLocations[location as keyof typeof allLocations] || [];
  }

  goToLocationDetail(locationId: String) {
    this.router.navigate(['/location-details']);
  }
}
