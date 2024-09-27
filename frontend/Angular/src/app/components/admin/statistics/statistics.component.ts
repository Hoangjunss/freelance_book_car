import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Chart, registerables } from 'chart.js';
import { StatisticMonthYear } from '../../../models/response/statistics/StatisticMonthYear';
import { StatisticsService } from '../../../services/statistics/statistics.service';

Chart.register(...registerables);

@Component({
  selector: 'app-statistics',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './statistics.component.html',
  styleUrl: './statistics.component.css'
})
export class StatisticsComponent implements OnInit{

  statisticMonthYear?: StatisticMonthYear;

  currentDate = new Date();
  currentMonth = this.currentDate.getMonth() + 1; // Vì getMonth() trả về giá trị từ 0-11, nên cộng thêm 1
  currentYear = this.currentDate.getFullYear();

  months = [
    { name: 'January', value: 1 },
    { name: 'February', value: 2 },
    { name: 'March', value: 3 },
    { name: 'April', value: 4 },
    { name: 'May', value: 5 },
    { name: 'June', value: 6 },
    { name: 'July', value: 7 },
    { name: 'August', value: 8 },
    { name: 'September', value: 9 },
    { name: 'October', value: 10 },
    { name: 'November', value: 11 },
    { name: 'December', value: 12 },
  ];
  years: number[] = [];
  selectedMonth: number = 0;
  selectedYear: number = 0;


  constructor(private statisticService: StatisticsService){}

  ngOnInit(): void {
    this.getStatisticMonthYear(this.currentMonth, this.currentYear);
    this.years = this.generateYears(2020, new Date().getFullYear());
    this.renderChart();
  }

  getStatisticMonthYear(month: number, year: number){
    this.statisticService.getStatisticMonthYear(month, year).subscribe({
      next: (data) => {
        this.statisticMonthYear = data;
        if (this.statisticMonthYear) {
          console.log('Tour created successfully:', this.statisticMonthYear);
        }
      },
      error: (err) => {
        console.error('Error statistic:', err.message);
      }
    });
  }


  generateYears(start: number, end: number): number[] {
    const years = [];
    for (let year = start; year <= end; year++) {
      years.push(year);
    }
    return years;
  }

  filterTours() {
    this.getStatisticMonthYear(this.selectedMonth, this.selectedYear);
  }

  renderChart() {
    const ctx = (document.getElementById('worldwide-sales') as HTMLCanvasElement).getContext('2d');
    if(ctx!=null){
      const chart = new Chart(ctx, {
        type: 'bar', // Bạn có thể thay đổi kiểu biểu đồ ở đây
        data: {
          labels: [
            'January', 'February', 'March', 'April', 'May', 'June',
            'July', 'August', 'September', 'October', 'November', 'December'
          ],
          datasets: [
            {
              label: 'Sales A',
              data: [30, 45, 70, 60, 90, 80, 100, 50, 75, 90, 85, 100],
              backgroundColor: 'rgba(75, 192, 192, 0.6)',
              borderColor: 'rgba(75, 192, 192, 1)',
              borderWidth: 1,
            },
            {
              label: 'Sales B',
              data: [20, 30, 50, 40, 60, 70, 80, 40, 60, 70, 80, 90],
              backgroundColor: 'rgba(153, 102, 255, 0.6)',
              borderColor: 'rgba(153, 102, 255, 1)',
              borderWidth: 1,
            },
            {
              label: 'Sales C',
              data: [10, 20, 30, 20, 40, 30, 50, 20, 40, 50, 60, 70],
              backgroundColor: 'rgba(255, 159, 64, 0.6)',
              borderColor: 'rgba(255, 159, 64, 1)',
              borderWidth: 1,
            },
          ],
        },
        options: {
          responsive: true,
          scales: {
            y: {
              beginAtZero: true
            }
          },
        },
      });
    }
    
  }

}
