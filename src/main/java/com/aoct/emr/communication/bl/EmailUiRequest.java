package com.aoct.emr.communication.bl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailUiRequest {
	
	private String recipient;
    private String msgBody;
    private String subject;
	private String startTime;
	private String endTime;
	private String date;

}
