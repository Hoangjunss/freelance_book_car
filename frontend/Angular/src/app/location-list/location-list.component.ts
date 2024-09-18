import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-location-list',
  imports:[CommonModule],
  standalone: true,
  templateUrl: './location-list.component.html',
  styleUrls: ['./location-list.component.css']
})
export class LocationListComponent implements OnInit {
  location: string | null = null;
  locations: { name: string, imageUrl: string }[] = [];

  constructor(private route: ActivatedRoute) { }

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
        { name: 'Ngũ Hành Sơn', imageUrl: 'https://example.com/nguhanhson.jpg' },
        { name: 'Ngũ Hành Sơn', imageUrl: 'https://example.com/nguhanhson.jpg' },
        { name: 'Ngũ Hành Sơn', imageUrl: 'https://example.com/nguhanhson.jpg' },
        { name: 'Ngũ Hành Sơn', imageUrl: 'https://example.com/nguhanhson.jpg' },
        { name: 'Ngũ Hành Sơn', imageUrl: 'https://example.com/nguhanhson.jpg' },
        { name: 'Ngũ Hành Sơn', imageUrl: 'https://example.com/nguhanhson.jpg' },
        { name: 'Ngũ Hành Sơn', imageUrl: 'https://example.com/nguhanhson.jpg' },
      ],
      'Hội An': [
        { name: 'Phố cổ Hội An', imageUrl: 'https://example.com/phoco.jpg' },
        { name: 'Cầu Nhật Bản', imageUrl: 'https://example.com/cau.jpg' },
        { name: 'Cầu Nhật Bản', imageUrl: 'https://example.com/cau.jpg' },
        { name: 'Cầu Nhật Bản', imageUrl: 'https://example.com/cau.jpg' },
        { name: 'Cầu Nhật Bản', imageUrl: 'https://example.com/cau.jpg' },
        { name: 'Cầu Nhật Bản', imageUrl: 'https://example.com/cau.jpg' },
        { name: 'Cầu Nhật Bản', imageUrl: 'https://example.com/cau.jpg' },
        { name: 'Cầu Nhật Bản', imageUrl: 'https://example.com/cau.jpg' },
        { name: 'Cầu Nhật Bản', imageUrl: 'https://example.com/cau.jpg' },
      ],
      'Bà Nà': [
        { name: 'Cầu Vàng', imageUrl: 'https://example.com/cauvong.jpg' },
        { name: 'Khu du lịch Bà Nà', imageUrl: 'https://example.com/bana.jpg' },
        { name: 'Khu du lịch Bà Nà', imageUrl: 'https://example.com/bana.jpg' },
        { name: 'Khu du lịch Bà Nà', imageUrl: 'https://example.com/bana.jpg' },
        { name: 'Khu du lịch Bà Nà', imageUrl: 'https://example.com/bana.jpg' },
        { name: 'Khu du lịch Bà Nà', imageUrl: 'https://example.com/bana.jpg' },
        { name: 'Khu du lịch Bà Nà', imageUrl: 'https://example.com/bana.jpg' },
        { name: 'Khu du lịch Bà Nà', imageUrl: 'https://example.com/bana.jpg' },
        { name: 'Khu du lịch Bà Nà', imageUrl: 'https://example.com/bana.jpg' },
      ],
      'Sân Bay': [
        { name: 'Sân bay Đà Nẵng', imageUrl: 'https://example.com/sanbay.jpg' },
        { name: 'Sân bay Đà Nẵng', imageUrl: 'https://example.com/sanbay.jpg' },
        { name: 'Sân bay Đà Nẵng', imageUrl: 'https://example.com/sanbay.jpg' },
        { name: 'Sân bay Đà Nẵng', imageUrl: 'https://example.com/sanbay.jpg' },
        { name: 'Sân bay Đà Nẵng', imageUrl: 'https://example.com/sanbay.jpg' },
        { name: 'Sân bay Đà Nẵng', imageUrl: 'https://example.com/sanbay.jpg' },
        { name: 'Sân bay Đà Nẵng', imageUrl: 'https://example.com/sanbay.jpg' },
        { name: 'Sân bay Đà Nẵng', imageUrl: 'https://example.com/sanbay.jpg' },
      ],
      'Nam Hội An': [
        { name: 'Khu du lịch Nam Hội An', imageUrl: 'https://example.com/namhoian.jpg' },
        { name: 'Khu du lịch Nam Hội An', imageUrl: 'https://example.com/namhoian.jpg' },
        { name: 'Khu du lịch Nam Hội An', imageUrl: 'https://example.com/namhoian.jpg' },
        { name: 'Khu du lịch Nam Hội An', imageUrl: 'https://example.com/namhoian.jpg' },
        { name: 'Khu du lịch Nam Hội An', imageUrl: 'https://example.com/namhoian.jpg' },
        { name: 'Khu du lịch Nam Hội An', imageUrl: 'https://example.com/namhoian.jpg' },
        { name: 'Khu du lịch Nam Hội An', imageUrl: 'https://example.com/namhoian.jpg' },
        { name: 'Khu du lịch Nam Hội An', imageUrl: 'https://example.com/namhoian.jpg' },
        { name: 'Khu du lịch Nam Hội An', imageUrl: 'https://example.com/namhoian.jpg' },
      ]
    };
    return allLocations[location as keyof typeof allLocations] || [];
  }
}
