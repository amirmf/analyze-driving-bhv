package com.taraan.dum.admin.service.rest;

import com.taraan.dum.dto.voucher.CreateMultipleVoucherDto;
import com.taraan.dum.dto.voucher.CreateVoucherDto;
import com.taraan.dum.dto.voucher.VoucherDto;
import com.taraan.dum.dto.voucher.VoucherPage;
import com.taraan.dum.service.assembler.VoucherAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created on 6/27/2018 12:00 PM.
 * User: Reza
 * Project : driving-usage-miner
 */

@Path("/voucher")
@Component
public class RestVoucherService {
    @Autowired
    private VoucherAssembler voucherAssembler;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createVoucher(CreateVoucherDto voucherDto) {
        return Response.ok().entity(voucherAssembler.createVoucher(voucherDto)).build();
    }

    @POST
    @Path("/multiple")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createVoucher(CreateMultipleVoucherDto createMultipleVoucherDto) {
        voucherAssembler.createVouchers(createMultipleVoucherDto);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{reward}/{code}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response terminateVoucher(@PathParam("reward") Long reward, @PathParam("code") String code) {
        voucherAssembler.terminateVoucher(reward, code);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{reward}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response terminateVouchers(@PathParam("reward") Long reward) {
        voucherAssembler.terminateVouchersByReward(reward);
        return Response.ok().build();
    }

    @GET
    @Path("/list")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVouchers(@QueryParam("reward") Long reward,
                                @QueryParam("state") String state,
                                @QueryParam("code") String code,
                                @QueryParam("fromDate") String fromDate,
                                @QueryParam("toDate") String toDate,
                                @QueryParam("from") Long from,
                                @QueryParam("size") Long size) {
        VoucherPage vouchers = voucherAssembler.getVouchers(reward, state, code, fromDate, toDate, from, size);
        return Response.ok().entity(vouchers).build();
    }

    @GET
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVouchers(@PathParam("id") Long id) {
        VoucherDto voucherDto = voucherAssembler.getVoucher(id);
        return Response.ok().entity(voucherDto).build();
    }


}
