import { AfterViewInit, Component, OnInit } from '@angular/core';
declare var $: any;  // Khai báo jQuery

@Component({
  selector: 'app-tour-schedule',
  standalone: true,
  templateUrl: './tour-schedule.component.html',
  styleUrls: ['./tour-schedule.component.css']
})
export class TourScheduleComponent implements OnInit, AfterViewInit {

  ngOnInit(): void {
  }

  ngAfterViewInit(): void {
    $('#calender').datetimepicker({
      inline: true,
      format: 'Y-m-d H:i'  // Đảm bảo định dạng phù hợp
    });
  } 
}
