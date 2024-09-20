import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { trigger, transition, style, animate, query, stagger } from '@angular/animations';

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
        { name: 'Đà Nẵng - Bà Nà (One way)', imageUrl: 'assets/img/HÀNH TRÌNH/HÀNH TRÌNH/2. Đà Nẵng - Bà Nà (One way - Round Trip)/Đà Nẵng - Bà Nà (One way).jpg' },
        { name: 'Đà Nẵng - Bà Nà (Round trip)', imageUrl: 'assets/img/HÀNH TRÌNH/HÀNH TRÌNH/2. Đà Nẵng - Bà Nà (One way - Round Trip)/Đà Nẵng - Bà Nà (Round trip).jpg' },
        { name: 'Đà Nẵng - Linh ứng (Round Trip)', imageUrl: 'assets/img/HÀNH TRÌNH/HÀNH TRÌNH/2. Đà Nẵng - Chùa Linh Ứng (One way - Round Trip)/Đà Nẵng - Linh ứng (Round Trip).jpg' },
        { name: 'Đà Nẵng - Hải Vân (Round Trip)', imageUrl: 'assets/img/HÀNH TRÌNH/HÀNH TRÌNH/2. Đà Nẵng - Hải Vân (Round Trip)/Đà Nẵng - Hải Vân (Round Trip).jpg' },
        { name: 'Đà Nẵng - Cố đô Huế (One way) ', imageUrl: 'assets/img/HÀNH TRÌNH/HÀNH TRÌNH/2. Đà Nẵng - Huế (One way - Round trip)/Đà Nẵng - Cố đô Huế (One way) 1.jpg' },
        { name: 'Đà Nẵng - Cố đô Huế (Round Trip)', imageUrl: 'assets/img/HÀNH TRÌNH/HÀNH TRÌNH/2. Đà Nẵng - Huế (One way - Round trip)/Đà Nẵng - Cố đô Huế (Round Trip).jpg' },
        { name: 'Đà Nẵng - Intercontinental (One way)', imageUrl: 'assets/img/HÀNH TRÌNH/HÀNH TRÌNH/2. Đà Nẵng - Intercontinent (One way - Round Trip)/Đà Nẵng - Intercontinental (One way).jpg' },
        { name: 'Đà Nẵng - Intercontinental (Round Trip)', imageUrl: 'assets/img/HÀNH TRÌNH/HÀNH TRÌNH/2. Đà Nẵng - Bà Nà (One way - Round Trip)/Đà Nẵng - Bà Nà (Round trip).jpg' },
        { name: 'Đà Nẵng - Mikazuki (One way) ', imageUrl: 'assets/img/HÀNH TRÌNH/HÀNH TRÌNH/2. Đà Nẵng - Mikazuki (One way - Round Trip)/Đà Nẵng - Mikazuki (One way) 1.jpg' },
        { name: 'Đà Nẵng - Mikazuki (Round trip)', imageUrl: 'assets/img/HÀNH TRÌNH/HÀNH TRÌNH/2. Đà Nẵng - Mikazuki (One way - Round Trip)/Đà Nẵng - Mikazuki (Round trip).jpg' },
        { name: 'Đà Nẵng - Núi Cẩm Thạch - Chùa Linh Ứng - Bà Nà - Đà Nẵng (Round Trip)', imageUrl: 'assets/img/HÀNH TRÌNH/HÀNH TRÌNH/2. Đà Nẵng - Núi Cẩm Thạch - Chùa Linh Ứng - Bà Nà - Đà Nẵng (Round Trip)/Đà Nẵng - Núi Cẩm Thạch - Chùa Linh Ứng - Bà Nà - Đà Nẵng (Round Trip).jpg' },
        { name: 'Đà Nẵng - Núi Cẩm Thạch - Chùa Linh Ứng - Đà Nẵng (Round Trip)', imageUrl: 'assets/img/HÀNH TRÌNH/HÀNH TRÌNH/2. Đà Nẵng - Núi Cẩm Thạch - Chùa Linh Ứng - Đà Nẵng (Round Trip)/Đà Nẵng - Núi Cẩm Thạch - Chùa Linh Ứng - Đà Nẵng (Round Trip).jpg' },
        { name: 'Đà Nẵng - Núi Cẩm Thạch - Chùa Linh Ứng - Hải Vân - Đà Nẵng (Round Trip)', imageUrl: 'assets/img/HÀNH TRÌNH/HÀNH TRÌNH/2. Đà Nẵng - Núi Cẩm Thạch - Chùa Linh Ứng - Hải Vân - Đà Nẵng (Round Trip)/1. Đà Nẵng - Núi Cẩm Thạch - Chùa Linh Ứng - Hải Vân - Đà Nẵng (Round Trip).jpg' },
        { name: 'Đà Nẵng - Núi Cẩm Thạch (Round trip)', imageUrl: 'assets/img/HÀNH TRÌNH/HÀNH TRÌNH/2. Đà Nẵng - Núi Cẩm Thạch (Round Trip)/Đà Nẵng - Núi Cẩm Thạch (Round trip).jpg' },
        { name: 'Đà Nẵng - Núi Thần Tài (One way)', imageUrl: 'assets/img/HÀNH TRÌNH/HÀNH TRÌNH/2. Đà Nẵng - Núi Thần Tài (One way - Round Trip)/ĐN - Núi Thần Tài (One way).jpg' },
        { name: 'Đà Nẵng - Núi Thần Tài (Round trip)', imageUrl: 'assets/img/HÀNH TRÌNH/HÀNH TRÌNH/2. Đà Nẵng - Núi Thần Tài (One way - Round Trip)/ĐN - Núi Thần Tài (Round trip).jpg' },
      ],
      'Hội An': [
        { name: 'Hội An - Huế (One way)', imageUrl: 'assets/img/HÀNH TRÌNH/HÀNH TRÌNH/4. Hội An - Bà Nà (One way - Round trip)/Hội An - Huế (One way).jpg' },
        { name: 'Hội An - Huế (One way)', imageUrl: 'assets/img/HÀNH TRÌNH/HÀNH TRÌNH/4. Hội An - Đà Nẵng (One way - Round trip)/Hội An - Huế (One way).jpg' },
        { name: 'Hội An - Huế (One way)', imageUrl: 'assets/img/HÀNH TRÌNH/HÀNH TRÌNH/4. Hội An - Huế (One way - Round trip)/Hội An - Huế (One way).jpg' },
        { name: 'Hội An - Huế (One way)', imageUrl: 'assets/img/HÀNH TRÌNH/HÀNH TRÌNH/4. Hội An - Huế (One way - Round trip)/Hội An - Huế (One way).jpg' },
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
        { name: 'Sân bay - Bà Nà (One way)', imageUrl: 'assets/img/HÀNH TRÌNH/HÀNH TRÌNH/3. Sân bay - Bà Nà (One way)/Sân bay - Bà Nà (One way) 1.jpg' },
        { name: 'Sân bay - Bà Nà (One way)', imageUrl: 'assets/img/HÀNH TRÌNH/HÀNH TRÌNH/3. Sân bay - Bà Nà (One way)/Sân bay - Bà Nà (One way) 2.jpg' },
        { name: 'Sân bay - Cocobay Resort, Shilla, Nam An Resort, Ocean Villa, Sheraton (One way)', imageUrl: 'assets/img/HÀNH TRÌNH/HÀNH TRÌNH/3. Sân bay - Cocobay Resort, Shilla, Nam An Resort, Ocean Villa, Sheraton (One way)/Sân bay - Cocobay Resort, Shilla, Nam An Resort, Ocean Villa, Sheraton (One way).jpg' },
        { name: 'Sân bay - Hội An (One way)', imageUrl: 'assets/img/HÀNH TRÌNH/HÀNH TRÌNH/3. Sân bay - Mỹ Sơn - Đà Nẵng (Round trip)/Sân bay - Mỹ Sơn (Round trip).jpg' },
        { name: 'Sân bay - Cố đô Huế (One way)', imageUrl: 'assets/img/HÀNH TRÌNH/HÀNH TRÌNH/3. Sân bay - Huế (One way)/Sân bay - Cố đô Huế (One way).jpg' },
        { name: 'Sân bay - Intercontinental (One way)', imageUrl: 'assets/img/HÀNH TRÌNH/HÀNH TRÌNH/3. Sân bay - Intercontinental (One way)/Sân bay - Intercontinental (One way).jpg' },
        { name: 'Sân bay - Mikazuki (One way)', imageUrl: 'assets/img/HÀNH TRÌNH/HÀNH TRÌNH/3. Sân bay - Mikazuki (One way)/Sân bay - Mikazuki (One way) 2.jpg' },
        { name: 'Sân bay - Mikazuki (One way)', imageUrl: 'assets/img/HÀNH TRÌNH/HÀNH TRÌNH/3. Sân bay - Mikazuki (One way)/Sân bay - Mikazuki (One way) 2.jpg' },
        { name: 'Sân bay - Mỹ Sơn (Round trip)', imageUrl: 'assets/img/HÀNH TRÌNH/HÀNH TRÌNH/3. Sân bay - Mỹ Sơn - Đà Nẵng (Round trip)/Sân bay - Mỹ Sơn (Round trip).jpg' },
        { name: 'Sân bay - Núi Cẩm Thạch - Chùa Linh Ứng - Đà Nẵng (Round Trip)', imageUrl: 'assets/img/HÀNH TRÌNH/HÀNH TRÌNH/3. Sân bay - Núi Cẩm Thạch - Chùa Linh Ứng - Đà Nẵng (Round Trip)/Sân bay - Núi Cẩm Thạch - Chùa Linh Ứng - Đà Nẵng (Round Trip).jpg' },
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
