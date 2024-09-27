package com.freelance.bookCar.services.statistic.statisticYear;

import com.freelance.bookCar.dto.response.statistic.StatisticMonth;
import com.freelance.bookCar.dto.response.statistic.StatisticYear;
import com.freelance.bookCar.models.invoice.Invoice;
import com.freelance.bookCar.models.invoice.InvoiceDetail;
import com.freelance.bookCar.respository.invoice.InvoiceDetailRepository;
import com.freelance.bookCar.respository.invoice.InvoiceResponsitory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class StatisticYearServiceImpl implements StatisticYearService{
    @Autowired
    private InvoiceResponsitory invoiceResponsitory;
    @Autowired
    private InvoiceDetailRepository invoiceDetailRepository;
    @Override
    public StatisticYear getYear(int year) {
        Invoice invoice=invoiceResponsitory.findByYear(year);
        List<StatisticMonth> statisticMonths= new ArrayList<>();
        for (int i=1;i<=12;i++){
            List<InvoiceDetail> invoiceDetailsHotel=invoiceDetailRepository.findByMonthAndHotel(invoice.getId());
            List<InvoiceDetail> invoiceDetailsTour=invoiceDetailRepository.findByMonthAndTour(invoice.getId());
            List<InvoiceDetail> invoiceDetailsTourism=invoiceDetailRepository.findByMonthAndTourism(invoice.getId());
            double priceHotel=0;
            double priceTour=0;
            double priceHTourism=0;
            for (InvoiceDetail invoiceDetail:invoiceDetailsHotel){
                priceHotel+=invoiceDetail.getTotalPrice();
            }
            for (InvoiceDetail invoiceDetail:invoiceDetailsTour){
                priceTour+=invoiceDetail.getTotalPrice();
            }
            for (InvoiceDetail invoiceDetail:invoiceDetailsTourism){
                priceHTourism+=invoiceDetail.getTotalPrice();
            }
            StatisticMonth statisticMonth=StatisticMonth.builder()
                    .month(i)
                    .priceHotel(priceHotel)
                    .priceTourism(priceHTourism)
                    .priceTour(priceTour)
                    .build();
            statisticMonths.add(statisticMonth);

        }

        return StatisticYear.builder().statisticMonths(statisticMonths).build();
    }
}
