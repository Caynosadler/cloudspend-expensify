package com.cloudspend.accountingsystem.service;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
public class ExpensifyService {

    private String reportTemplate = "<#list reports as report><#list report.transactionList as expense><#if expense.modifiedMerchant?has_content><#assign merchant = expense.modifiedMerchant><#else><#assign merchant = expense.merchant></#if><#if expense.convertedAmount?has_content><#assign amount = expense.convertedAmount/100><#elseif expense.modifiedAmount?has_content><#assign amount = expense.modifiedAmount/100><#else><#assign amount = expense.amount/100></#if><#assign reciept = expense.receiptObject.url/100><#assign category = expense.category/100><#if expense.modifiedCreated?has_content><#assign created = expense.modifiedCreated><#else><#assign created = expense.created></#if>${merchant},<#t>${amount},<#t>${created},<#t>${expense.category}/n<#t></#list></#list>";

    @Value("${expensify.endpoint}")
    private String urlEndpoint;

    @Autowired
    private RestTemplate restTemplate;

    private JSONObject credentials;

    public ExpensifyService(){
        credentials = new JSONObject();
        credentials.appendField("partnerUserID", "aa_livecity505_gmail_com");
        credentials.appendField("partnerUserSecret", "78e5433e350030b7aa23ada99c6b6aedaea0cb64");
    }

    public String getTransaction(String date){
        ArrayList<String> immediateResponse = new ArrayList<>();
        immediateResponse.add("returnRandomFileName");

        JSONObject onReceive = new JSONObject()
                .appendField("immediateResponse", immediateResponse);
        JSONObject inputSettings = new JSONObject()
                .appendField("type", "combinedReportData")
                .appendField("filters", new JSONObject().appendField("startDate", date));
        JSONObject outputSettings = new JSONObject()
                .appendField("fileExtension", "json");

        JSONObject requestBody = new JSONObject()
                .appendField("type", "file")
                .appendField("credentials", credentials)
                .appendField("onReceive", onReceive)
                .appendField("inputSettings", inputSettings)
                .appendField("outputSettings", outputSettings);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("requestJobDescription", requestBody.toJSONString());
        params.add("template", reportTemplate);

        HttpEntity<?> entity = new HttpEntity<>(params, headers);
        HttpEntity<String> response = restTemplate.exchange(
                urlEndpoint,
                HttpMethod.POST,
                entity,
                String.class
        );

        return downloadExportedReport(response.getBody());

    }

    private String downloadExportedReport(String filename){
        JSONObject requestBody = new JSONObject();
        requestBody.appendField("type", "download");
        requestBody.appendField("credentials", credentials);
        requestBody.appendField("fileName", filename);
        requestBody.appendField("fileSystem", "integrationServer");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("requestJobDescription", requestBody.toJSONString());

        HttpEntity<?> entity = new HttpEntity<>(params, headers);
        HttpEntity<String> response = restTemplate.exchange(
                urlEndpoint,
                HttpMethod.POST,
                entity,
                String.class
        );
        return response.getBody();
    }
}
