package com.freelance.bookCar.services.statistic.statisticYear;

import com.freelance.bookCar.dto.response.invoiceDTO.GetInvoiceDetailResponse;
import com.freelance.bookCar.dto.response.invoiceDTO.GetInvoiceResponse;
import com.freelance.bookCar.dto.response.statistic.statisticMonthYear.StatisticMonthYear;
import com.freelance.bookCar.dto.response.statistic.statisticYear.StatisticMonth;
import com.freelance.bookCar.dto.response.statistic.statisticYear.StatisticYear;
import com.freelance.bookCar.models.invoice.Invoice;
import com.freelance.bookCar.models.invoice.InvoiceDetail;
import com.freelance.bookCar.respository.invoice.InvoiceDetailRepository;
import com.freelance.bookCar.respository.invoice.InvoiceResponsitory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StatisticYearServiceImpl implements StatisticYearService{
    @Autowired
    private InvoiceResponsitory invoiceResponsitory;
    @Autowired
    private InvoiceDetailRepository invoiceDetailRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public StatisticYear getYear(int year) {
        List<StatisticMonth> statisticMonths= new ArrayList<>();
        for (int i=1;i<=12;i++){
            List<InvoiceDetail> invoiceDetailsHotel=invoiceDetailRepository.findByMonthAndHotel(year);
            List<InvoiceDetail> invoiceDetailsTour=invoiceDetailRepository.findByMonthAndTour(year);
            List<InvoiceDetail> invoiceDetailsTourism=invoiceDetailRepository.findByMonthAndTourism(year);
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

    @Override
    public StatisticMonthYear getMonth(int year,int month) {
        List<GetInvoiceDetailResponse> getInvoiceResponses=invoiceDetailRepository.findByIdInvoiceAndMonthYear(year,month)
                .stream().map(invoiceDetail ->
                        modelMapper.map(invoiceDetail, GetInvoiceDetailResponse.class))
                .collect(Collectors.toList());
         double totalPrice=0;
         double avgPrice=0;
         double totalTicket=0;
         double avgTicket=0;
        for (GetInvoiceDetailResponse getInvoiceResponse: getInvoiceResponses){
            totalPrice+=getInvoiceResponse.getTotalPrice();
            totalTicket+=getInvoiceResponse.getQuantity();
        }
        if(month==1||month==3||month==5||month==7||month==8||month==10||month==12){
            avgPrice=totalPrice/31;
            avgTicket=totalTicket/31;
        }
        if(month==4||month==6||month==9||month==11){
            avgPrice=totalPrice/30;
            avgTicket=totalTicket/30;
        }if(month==2){
            avgPrice=totalPrice/28;
            avgTicket=totalTicket/28;
        }

         return StatisticMonthYear.builder()
                 .avgPrice(avgPrice)
                 .avgTicket(avgTicket)
                 .totalTicket(totalTicket)
                 .totalPrice(totalPrice)
                 .build();
    }
}
