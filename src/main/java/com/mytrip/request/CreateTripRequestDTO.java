package com.mytrip.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mytrip.pojo.TripReason;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class CreateTripRequestDTO {

    private TripReason tripReason;

    @NotEmpty
    private String tripDescription;

    @NotEmpty
    private String tripFrom;

    @NotEmpty
    private String tripTo;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date departureDate;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date arrivalDate;
}
