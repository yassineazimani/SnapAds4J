/*
 * Copyright 2019 Yassine AZIMANI
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package snapads4j.utils;

/**
 * Mock Response HTTP (body).
 *
 * @author Yassine
 */
public class SnapResponseUtils {

  public static String getSnapOAuthToken() {
    return "{\r\n  \"expires_in\": 1800,\r\n  \"token_type\": \"Bearer\",\r\n  \"refresh_token\": \"32eb12f037712a6b60404d6d9c170ee9ae4d5b9936c73dd03c23fffff1213cb3\",\r\n  \"access_token\": \"0.MGQCxyz123\"\r\n}";
  } // getSnapOAuthToken()

  public static String getSnapRefreshToken() {
    return "{\r\n    \"expires_in\": 1800,\r\n    \"token_type\": \"Bearer\",\r\n    \"refresh_token\": \"xyz\",\r\n    \"access_token\": \"0.1234567890\"\r\n}";
  } // getSnapRefreshToken()

  public static String getSnapAuthenticatedUser() {
    return "{\r\n  \"me\": {\r\n    \"id\": \"2f5dd7e6-fcd1-4324-8455-1ea4d96caaaa\",\r\n    \"updated_at\": \"2016-08-12T01:56:39.841Z\",\r\n    \"created_at\": \"2016-08-12T01:56:39.842Z\",\r\n    \"email\": \"honey.badger@hooli.com\",\r\n    \"organization_id\": \"40d6719b-da09-410b-9185-0cc9c0dfed1d\",\r\n    \"display_name\": \"Honey Badger\"\r\n  },\r\n  \"request_id\": \"57ae093a00ff00ff43b8c63fda390001737e616473617069736300016275696c642d34363138393265642d312d31312d32000100\"\r\n}";
  } // getSnapAuthenticatedUser()

  public static String getSnapAllOrganizations() {
    return "{\r\n  \"request_status\": \"success\",\r\n  \"request_id\": \"57affee300ff0d91229fabb9710001737e616473617069736300016275696c642d35396264653638322d312d31312d3700010110\",\r\n  \"organizations\": [\r\n    {\r\n      \"sub_request_status\": \"success\",\r\n      \"organization\": {\r\n        \"id\": \"40d6719b-da09-410b-9185-0cc9c0dfed1d\",\r\n        \"updated_at\": \"2017-05-26T15:14:44.877Z\",\r\n        \"created_at\": \"2017-05-26T15:14:44.877Z\",\r\n        \"name\": \"My Organization\",\r\n        \"address_line_1\": \"101 Stewart St\",\r\n        \"locality\": \"Seattle\",\r\n        \"administrative_district_level_1\": \"WA\",\r\n        \"country\": \"US\",\r\n        \"postal_code\": \"98134\",\r\n        \"type\": \"ENTERPRISE\"\r\n      }\r\n    },\r\n    {\r\n      \"sub_request_status\": \"success\",\r\n      \"organization\": {\r\n        \"id\": \"507d7a57-94de-4239-8a74-e93c00ca53e6\",\r\n        \"updated_at\": \"2016-08-01T15:14:44.877Z\",\r\n        \"created_at\": \"2017-08-01T15:14:44.877Z\",\r\n        \"name\": \"Hooli\",\r\n        \"address_line_1\": \"1100 Silicon Vallety Rd\",\r\n        \"locality\": \"San Francisco\",\r\n        \"administrative_district_level_1\": \"CA\",\r\n        \"country\": \"US\",\r\n        \"postal_code\": \"94110\",\r\n        \"type\": \"ENTERPRISE\"\r\n      }\r\n    }\r\n  ]\r\n}";
  } // getSnapAllOrganizations()

  public static String getSnapAllOrganizationsWithAdAccount() {
    return "{\r\n    \"request_status\": \"SUCCESS\",\r\n    \"request_id\": \"5b985a7300ff0b4b064df945b80001737e616473617069736300016275696c642d33346634346232622d312d3230302d320001011f\",\r\n    \"organizations\": [\r\n        {\r\n            \"sub_request_status\": \"SUCCESS\",\r\n            \"organization\": {\r\n                \"id\": \"40d6719b-da09-410b-9185-0cc9c0dfed1d\",\r\n                \"updated_at\": \"2018-09-04T16:27:01.066Z\",\r\n                \"created_at\": \"2016-08-09T17:12:49.707Z\",\r\n                \"name\": \"Hooli Inc\",\r\n                \"country\": \"US\",\r\n                \"postal_code\": \"90291\",\r\n                \"locality\": \"Venice\",\r\n                \"contact_name\": \"\",\r\n                \"contact_email\": \"\",\r\n                \"contact_phone\": \"\",\r\n                \"tax_type\": \"NONE\",\r\n                \"address_line_1\": \"64 Market St\",\r\n                \"administrative_district_level_1\": \"CA\",\r\n                \"partner_orgs\": {\r\n                    \"507d7a57-94ae-4239-8a74-e93c00aa53e6\": \"Cyberdyne Corp. \",\r\n                    \"306bb6f4-e80c-4c9c-9e39-2a4d673998a8\": \"Initech Inc\",\r\n                    \"8c68b16f-03a6-4203-9480-f969fa1492d9\": \"Nextably App\",\r\n                    \"5b3e970a-fa32-4a6a-a51d-bbf483a6d405\": \"Givester App\",\r\n                    \"94da8871-921a-4e45-b032-8045795aecaf\": \"Pied Piper corp\"\r\n                },\r\n                \"accepted_term_version\": \"8\",\r\n                \"is_agency\": true,\r\n                \"configuration_settings\": {\r\n                    \"notifications_enabled\": true\r\n                },\r\n                \"type\": \"ENTERPRISE\",\r\n                \"state\": \"ACTIVE\",\r\n                \"roles\": [\r\n                    \"member\"\r\n                ],\r\n                \"ad_accounts\": [\r\n                    {\r\n                        \"id\": \"8b8e40af-fc64-455d-925b-ca80f7af6914\",\r\n                        \"updated_at\": \"2018-03-07T23:39:20.469Z\",\r\n                        \"created_at\": \"2018-03-27T23:42:43.513Z\",\r\n                        \"name\": \"Hooli Originals\",\r\n                        \"type\": \"PARTNER\",\r\n                        \"status\": \"ACTIVE\",\r\n                        \"currency\": \"USD\",\r\n                        \"timezone\": \"America/Los_Angeles\",\r\n                        \"roles\": [\r\n                            \"reports\"\r\n                        ]\r\n                    },\r\n                    {\r\n                        \"id\": \"497979f0-ea17-4971-8288-054883f1caca\",\r\n                        \"updated_at\": \"2017-01-04T22:58:38.179Z\",\r\n                        \"created_at\": \"2018-09-07T14:41:36.002Z\",\r\n                        \"name\": \"Pied piper Test Account\",\r\n                        \"type\": \"PARTNER\",\r\n                        \"status\": \"ACTIVE\",\r\n                        \"currency\": \"USD\",\r\n                        \"timezone\": \"America/Los_Angeles\",\r\n                        \"roles\": [\r\n                            \"admin\"\r\n                        ]\r\n                    },\r\n                    {\r\n                        \"id\": \"22ada972-f2aa-4d06-a45a-a7a80f53ae34\",\r\n                        \"updated_at\": \"2017-07-28T02:32:03.914Z\",\r\n                        \"created_at\": \"2017-12-08T21:56:57.305Z\",\r\n                        \"name\": \"Initech Corp\",\r\n                        \"type\": \"PARTNER\",\r\n                        \"status\": \"ACTIVE\",\r\n                        \"currency\": \"USD\",\r\n                        \"timezone\": \"America/Los_Angeles\",\r\n                        \"roles\": [\r\n                            \"creative\"\r\n                        ]\r\n                    }\r\n                ],\r\n                \"my_display_name\": \"Honey Badger\",\r\n                \"my_invited_email\": \"honey.badget@hooli.com\",\r\n                \"my_member_id\": \"8454ada6-cec8-4e97-a0a7-c0b262c4137b\"\r\n            }\r\n        }\r\n    ]\r\n}";
  } // getSnapAllOrganizationsWithAdAccount()

  public static String getSnapSpecificOrganization() {
    return "{\r\n  \"request_status\": \"SUCCESS\",\r\n  \"request_id\": \"5928472c00ff04f7eaae709d770001737e7465616d6b6f363139000161646d616e616765722d61706\",\r\n  \"organizations\": [\r\n    {\r\n      \"sub_request_status\": \"SUCCESS\",\r\n      \"organization\": {\r\n        \"id\": \"40d6719b-da09-410b-9185-0cc9c0dfed1d\",\r\n        \"updated_at\": \"2017-05-26T15:14:44.877Z\",\r\n        \"created_at\": \"2017-05-26T15:14:44.877Z\",\r\n        \"name\": \"My Organization\",\r\n        \"address_line_1\": \"101 Stewart St\",\r\n        \"locality\": \"Seattle\",\r\n        \"administrative_district_level_1\": \"WA\",\r\n        \"country\": \"US\",\r\n        \"postal_code\": \"98134\",\r\n        \"type\": \"ENTERPRISE\"\r\n      }\r\n    }\r\n  ]\r\n}";
  } // getSnapSpecificOrganization()

  public static String getSnapAllFundingSources() {
    return "{\r\n  \"request_status\": \"SUCCESS\",\r\n  \"request_id\": \"59236a2500ff05763ff95f2fcc0001737e7465616d6b6f363139000161646d616e616765722d6170693a6b6162726168616d0001011d\",\r\n  \"fundingsources\": [\r\n    {\r\n      \"sub_request_status\": \"SUCCESS\",\r\n      \"fundingsource\": {\r\n        \"id\": \"1e224e75-3883-42cf-a5d9-ce505945d2d3\",\r\n        \"updated_at\": \"2017-05-22T22:46:30.917Z\",\r\n        \"created_at\": \"2017-05-22T22:46:30.917Z\",\r\n        \"type\": \"CREDIT_CARD\",\r\n        \"card_type\": \"DISCOVER\",\r\n        \"name\" : \"My DISCOVER card\",\r\n        \"last_4\": \"1100\",\r\n        \"expiration_month\": \"12\",\r\n        \"expiration_year\": \"2020\",\r\n        \"daily_spend_limit_micro\": 25000000,\r\n        \"daily_spend_currency\": \"USD\"\r\n      }\r\n    },\r\n    {\r\n      \"sub_request_status\": \"SUCCESS\",\r\n      \"fundingsource\": {\r\n        \"id\": \"9d111fbf-da5f-4526-9e7b-226f847b3d7e\",\r\n        \"updated_at\": \"2017-05-22T22:46:30.920Z\",\r\n        \"created_at\": \"2017-05-22T22:46:30.920Z\",\r\n        \"type\": \"LINE_OF_CREDIT\",\r\n        \"available_credit_micro\": 2000000000,\r\n        \"currency\": \"USD\",\r\n        \"total_budget_micro\": 10000000000,\r\n        \"status\": \"ACTIVE\",\r\n        \"credit_account_type\": \"MANAGED\",\r\n        \"budget_spent_micro\": 8000000000\r\n      }\r\n    },\r\n    {\r\n      \"sub_request_status\": \"SUCCESS\",\r\n      \"fundingsource\": {\r\n        \"id\": \"d24b4011-3560-47ea-86fa-0ed14c6b90d4\",\r\n        \"updated_at\": \"2017-05-22T22:46:30.920Z\",\r\n        \"created_at\": \"2017-05-22T22:46:30.920Z\",\r\n        \"type\": \"COUPON\",\r\n        \"available_credit_micro\": 10000000000,\r\n        \"currency\": \"EUR\",\r\n        \"value_micro\": 10000000000,\r\n        \"status\": \"REDEEMED\",\r\n        \"start_date\": \"2017-05-22T22:46:30.923Z\",\r\n        \"end_date\": \"2017-05-22T22:46:30.923Z\"\r\n      }\r\n    }\r\n  ]\r\n}";
  } // getSnapAllFundingSources()

  public static String getSnapSpecificFundingSource() {
    return "{\r\n  \"request_status\": \"success\",\r\n  \"request_id\": \"57b000ffd800ff05551a197221f10001737e616473617069736300016275696c642d35396264653638322d312d31312d370001010c\",\r\n  \"fundingsources\": [\r\n    {\r\n      \"sub_request_status\": \"success\",\r\n      \"fundingsource\": {\r\n        \"id\": \"e703eb9f-8eac-4eda-a9c7-deec3935222d\",\r\n        \"updated_at\": \"2016-08-11T22:03:54.337Z\",\r\n        \"created_at\": \"2016-08-11T22:03:54.337Z\",\r\n        \"name\": \"Hooli Test Ad Account Funding Source\",\r\n        \"type\": \"LINE_OF_CREDIT\",\r\n        \"organization_id\": \"40d6719b-da09-410b-9185-0cc9c0dfed1d\",\r\n        \"currency\": \"USD\"\r\n      }\r\n    }\r\n  ]\r\n}";
  } // getSnapSpecificFundingSource()

  public static String getSnapAllAdAccounts() {
    return "{\r\n  \"request_status\": \"success\",\r\n  \"request_id\": \"57b0015e00ff07d5c0c38928ad0001737e616473617069736300016275696c642d35396264653638322d312d31312d3700010107\",\r\n  \"adaccounts\": [\r\n    {\r\n      \"sub_request_status\": \"success\",\r\n      \"adaccount\": {\r\n        \"id\": \"8adc3db7-8148-4fbf-999c-8d2266369d74\",\r\n        \"updated_at\": \"2016-08-11T22:03:58.869Z\",\r\n        \"created_at\": \"2016-08-11T22:03:58.869Z\",\r\n        \"name\": \"Hooli Test Ad Account\",\r\n        \"type\": \"PARTNER\",\r\n        \"status\": \"ACTIVE\",\r\n        \"organization_id\": \"40d6719b-da09-410b-9185-0cc9c0dfed1d\",\r\n        \"funding_source_ids\": [\r\n          \"e703eb9f-8eac-4eda-a9c7-deec3935222d\"\r\n        ],\r\n        \"currency\": \"USD\",\r\n        \"timezone\": \"America\\/Los_Angeles\",\r\n        \"advertiser\": \"Hooli\"\r\n      }\r\n    },\r\n    {\r\n      \"sub_request_status\": \"success\",\r\n      \"adaccount\": {\r\n        \"id\": \"81cf9302-764c-429a-8561-e3bc329cf987\",\r\n        \"updated_at\": \"2016-08-11T22:03:58.869Z\",\r\n        \"created_at\": \"2016-08-11T22:03:58.869Z\",\r\n        \"name\": \"Awesome Ad Account\",\r\n        \"type\": \"DIRECT\",\r\n        \"status\": \"ACTIVE\",\r\n        \"organization_id\": \"40d6719b-da09-410b-9185-0cc9c0dfed1d\",\r\n        \"funding_source_ids\": [\r\n          \"7abfb9c6-0258-4eee-9898-03a8c099695d\"\r\n        ],\r\n        \"currency\": \"USD\",\r\n        \"timezone\": \"America\\/Los_Angeles\",\r\n        \"advertiser\": \"Hooli\"\r\n      }\r\n    }\r\n  ]\r\n}";
  } // getSnapAllAdAccounts()

  public static String getSnapSpecificAdAccount() {
    return "{\r\n \"request_status\": \"success\",\r\n \"request_id\": \"57b0021900ff06bbfeb44f77240001737e616473617069736300016275696c642d35396264653638322d312d31312d370001010e\",\r\n \"adaccounts\": [\r\n {\r\n \"sub_request_status\": \"success\",\r\n \"adaccount\": {\r\n \"id\": \"8adc3db7-8148-4fbf-999c-8d2266369d74\",\r\n \"updated_at\": \"2016-08-11T22:03:58.869Z\",\r\n \"created_at\": \"2016-08-11T22:03:58.869Z\",\r\n \"name\": \"Hooli Test Ad Account\",\r\n \"type\": \"PARTNER\",\r\n \"status\": \"ACTIVE\",\r\n \"organization_id\": \"40d6719b-da09-410b-9185-0cc9c0dfed1d\",\r\n \"funding_source_ids\": [\r\n \"e703eb9f-8eac-4eda-a9c7-deec3935222d\"\r\n ],\r\n \"currency\": \"USD\",\r\n \"timezone\": \"America\\/Los_Angeles\",\r\n \"advertiser\": \"Hooli\"\r\n }\r\n }\r\n ]\r\n}";
  } // getSnapSpecificAdAccount()
  
  public static String getSnapAdAccountUpdated() {
      return "{\r\n" + 
      	"  \"request_status\": \"success\",\r\n" + 
      	"  \"request_id\": \"57b0021900ff06bbfeb44f77240001737e616473617069736300016275696c642d35396264653638322d312d31312d370001010e\",\r\n" + 
      	"  \"adaccounts\": [\r\n" + 
      	"    {\r\n" + 
      	"      \"sub_request_status\": \"success\",\r\n" + 
      	"      \"adaccount\": {\r\n" + 
      	"        \"id\": \"123b9ca6-92f2-49c3-a3ed-0ea58afb467e\",\r\n" + 
      	"        \"updated_at\": \"2016-08-11T22:03:58.869Z\",\r\n" + 
      	"        \"created_at\": \"2016-08-11T22:03:58.869Z\",\r\n" + 
      	"        \"name\": \"Hooli Ad Account\",\r\n" + 
      	"        \"type\": \"PARTNER\",\r\n" + 
      	"        \"status\": \"ACTIVE\",\r\n" + 
      	"        \"organization_id\": \"40d6719b-da09-410b-9185-0cc9c0dfed1d\",\r\n" + 
      	"        \"funding_source_ids\": [\r\n" + 
      	"          \"cdc67eba-a774-4954-9b94-9502bbdac1bc\"\r\n" + 
      	"        ],\r\n" + 
      	"        \"currency\": \"USD\",\r\n" + 
      	"        \"timezone\": \"America/Los_Angeles\",\r\n" + 
      	"        \"brand_name\":\"Hooli\",\r\n" + 
      	"        \"lifetime_spend_cap_micro\":1500000000\r\n" + 
      	"      }\r\n" + 
      	"    }\r\n" + 
      	"  ]\r\n" + 
      	"}";
  }// getSnapAdAccountUpdated()
  
  public static String getSnapCampaignCreated() {
      return "{\r\n" + 
      	"  \"request_status\": \"success\",\r\n" + 
      	"  \"request_id\": \"57b002ad00ff07e1f50fd2267f0001737e616473617069736300016275696c642d35396264653638322d312d31312d370001010d\",\r\n" + 
      	"  \"campaigns\": [\r\n" + 
      	"    {\r\n" + 
      	"      \"sub_request_status\": \"success\",\r\n" + 
      	"      \"campaign\": {\r\n" + 
      	"        \"id\": \"92e1c28a-a331-45b4-8c26-fd3e0eea8c39\",\r\n" + 
      	"        \"updated_at\": \"2016-08-14T05:33:33.876Z\",\r\n" + 
      	"        \"created_at\": \"2016-08-14T05:33:33.876Z\",\r\n" + 
      	"        \"name\": \"Cool Campaign\",\r\n" + 
      	"        \"ad_account_id\": \"8adc3db7-8148-4fbf-999c-8d2266369d74\",\r\n" + 
      	"        \"status\": \"PAUSED\",\r\n" + 
      	"        \"start_time\": \"2016-08-11T22:03:58.869Z\"\r\n" + 
      	"      }\r\n" + 
      	"    }\r\n" + 
      	"  ]\r\n" + 
      	"}";
  }// getSnapCampaignCreated()
  
  public static String getSnapCampaignUpdated() {
      return "{\r\n" + 
      	"  \"request_status\": \"success\",\r\n" + 
      	"  \"request_id\": \"57b0032700ff07e0cfdaa5e1a40001737e616473617069736300016275696c642d35396264653638322d312d31312d3700010105\",\r\n" + 
      	"  \"campaigns\": [\r\n" + 
      	"    {\r\n" + 
      	"      \"sub_request_status\": \"success\",\r\n" + 
      	"      \"campaign\": {\r\n" + 
      	"        \"id\": \"92e1c28a-a331-45b4-8c26-fd3e0eea8c39\",\r\n" + 
      	"        \"updated_at\": \"2016-08-14T05:35:35.943Z\",\r\n" + 
      	"        \"created_at\": \"2016-08-14T05:33:33.876Z\",\r\n" + 
      	"        \"name\": \"Cool Campaign\",\r\n" + 
      	"        \"ad_account_id\": \"8adc3db7-8148-4fbf-999c-8d2266369d74\",\r\n" + 
      	"        \"status\": \"PAUSED\",\r\n" + 
      	"        \"start_time\": \"2016-08-11T22:03:58.869Z\",\r\n" + 
      	"        \"end_time\": \"2016-08-22T05:03:58.869Z\"\r\n" + 
      	"      }\r\n" + 
      	"    }\r\n" + 
      	"  ]\r\n" + 
      	"}";
  }// getSnapCampaignUpdated()

  public static String getSnapAllCampaigns() {
    return "{\r\n  \"request_status\": \"success\",\r\n  \"request_id\": \"57b003c700ff0f2e66c37f96c20001737e616473617069736300016275696c642d35396264653638322d312d31312d3700010103\",\r\n  \"campaigns\": [\r\n    {\r\n      \"sub_request_status\": \"success\",\r\n      \"campaign\": {\r\n        \"id\": \"06302efa-4c0f-4e36-b880-a395a36cef64\",\r\n        \"updated_at\": \"2016-08-12T20:28:58.738Z\",\r\n        \"created_at\": \"2016-08-12T20:28:58.738Z\",\r\n        \"name\": \"Campaign One\",\r\n        \"ad_account_id\": \"8adc3db7-8148-4fbf-999c-8d2266369d74\",\r\n        \"daily_budget_micro\": 200000000,\r\n        \"status\": \"ACTIVE\",\r\n        \"start_time\": \"2016-08-10T17:12:49.707Z\",\r\n        \"end_time\": \"2016-08-13T17:12:49.707Z\"\r\n      }\r\n    },\r\n    {\r\n      \"sub_request_status\": \"success\",\r\n      \"campaign\": {\r\n        \"id\": \"0fc8e179-6f3b-46e7-be8e-ca53fd404ece\",\r\n        \"updated_at\": \"2016-08-12T21:06:18.343Z\",\r\n        \"created_at\": \"2016-08-12T21:06:18.343Z\",\r\n        \"name\": \"Campaign Deux\",\r\n        \"ad_account_id\": \"8adc3db7-8148-4fbf-999c-8d2266369d74\",\r\n        \"daily_budget_micro\": 500000000,\r\n        \"status\": \"ACTIVE\",\r\n        \"start_time\": \"2016-08-10T17:12:49.707Z\",\r\n        \"end_time\": \"2016-08-13T17:12:49.707Z\"\r\n      }\r\n    },\r\n\r\n   \r\n\r\n    {\r\n      \"sub_request_status\": \"success\",\r\n      \"campaign\": {\r\n        \"id\": \"92e1c28a-a331-45b4-8c26-fd3e0eea8c39\",\r\n        \"updated_at\": \"2016-08-14T05:36:46.441Z\",\r\n        \"created_at\": \"2016-08-14T05:33:33.876Z\",\r\n        \"name\": \"Cool Campaign\",\r\n        \"ad_account_id\": \"8adc3db7-8148-4fbf-999c-8d2266369d74\",\r\n        \"status\": \"PAUSED\",\r\n        \"start_time\": \"2016-08-11T22:03:58.869Z\",\r\n        \"end_time\": \"2016-08-22T05:03:58.869Z\"\r\n      }\r\n    },\r\n    {\r\n      \"sub_request_status\": \"success\",\r\n      \"campaign\": {\r\n        \"id\": \"fedf8e04-0176-4ce3-a1ca-148204aee62c\",\r\n        \"updated_at\": \"2016-08-12T02:18:33.412Z\",\r\n        \"created_at\": \"2016-08-12T02:18:33.412Z\",\r\n        \"name\": \"Crazy Campaign\",\r\n        \"ad_account_id\": \"8adc3db7-8148-4fbf-999c-8d2266369d74\",\r\n        \"start_time\": \"2016-08-11T22:03:58.869Z\",\r\n        \"status\": \"PAUSED\"\r\n      }\r\n    }\r\n  ]\r\n}";
  } // getSnapAllCampaigns()

  public static String getSnapSpecificCampaign() {
    return "{\r\n  \"request_status\": \"success\",\r\n  \"request_id\": \"57b0049c00ff0e8cb21af5199c0001737e616473617069736300016275696c642d35396264653638322d312d31312d3700010107\",\r\n  \"campaigns\": [\r\n    {\r\n      \"sub_request_status\": \"success\",\r\n      \"campaign\": {\r\n        \"id\": \"92e1c28a-a331-45b4-8c26-fd3e0eea8c39\",\r\n        \"updated_at\": \"2016-08-14T05:36:46.441Z\",\r\n        \"created_at\": \"2016-08-14T05:33:33.876Z\",\r\n        \"name\": \"Cool Campaign\",\r\n        \"ad_account_id\": \"8adc3db7-8148-4fbf-999c-8d2266369d74\",\r\n        \"status\": \"PAUSED\",\r\n        \"start_time\": \"2016-08-11T22:03:58.869Z\",\r\n        \"end_time\": \"2016-08-22T05:03:58.869Z\"\r\n      }\r\n    }\r\n  ]\r\n}";
  } // getSnapSpecificCampaign()
  
  public static String getSnapAdSquadCreated() {
      return "{\r\n" + 
      	"  \"request_status\": \"success\",\r\n" + 
      	"  \"request_id\": \"57b007e000ff09058b02a9e0220001737e616473617069736300016275696c642d35396264653638322d312d31312d3700010110\",\r\n" + 
      	"  \"adsquads\": [\r\n" + 
      	"    {\r\n" + 
      	"      \"sub_request_status\": \"success\",\r\n" + 
      	"      \"adsquad\": {\r\n" + 
      	"        \"id\": \"69cde0e6-0ccb-4982-8fce-1fa33507f213\",\r\n" + 
      	"        \"updated_at\": \"2016-08-14T05:55:45.250Z\",\r\n" + 
      	"        \"created_at\": \"2016-08-14T05:55:45.250Z\",\r\n" + 
      	"        \"name\": \"AdSquad2019\",\r\n" + 
      	"        \"status\": \"PAUSED\",\r\n" + 
      	"        \"campaign_id\": \"6cf25572-048b-4447-95d1-eb48231751be\",\r\n" + 
      	"        \"type\": \"SNAP_ADS\",\r\n" + 
      	"        \"targeting\": {\r\n" + 
      	"          \"geos\": [\r\n" + 
      	"            {\r\n" + 
      	"              \"country_code\": \"us\"\r\n" + 
      	"            }\r\n" + 
      	"          ]\r\n" + 
      	"        },\r\n" + 
      	"        \"placement\": \"SNAP_ADS\",\r\n" + 
      	"        \"billing_event\": \"IMPRESSION\",\r\n" + 
      	"        \"bid_micro\": 1000000,\r\n" + 
      	"        \"daily_budget_micro\": 1000000000,\r\n" + 
      	"        \"start_time\": \"2016-08-11T22:03:58.869Z\",\r\n" + 
      	"        \"optimization_goal\": \"IMPRESSIONS\"\r\n" + 
      	"      }\r\n" + 
      	"    }\r\n" + 
      	"  ]\r\n" + 
      	"}";
  }// getSnapAdSquadCreated()
  
  public static String getSnapAdSquadUpdated() {
      return "{\r\n" + 
      	"  \"status\": \"success\",\r\n" + 
      	"  \"request_id\": \"5792746b00019a287b52009b\",\r\n" + 
      	"  \"adsquads\": [\r\n" + 
      	"    {\r\n" + 
      	"      \"status\": \"success\",\r\n" + 
      	"      \"adsquad\": {\r\n" + 
      	"        \"id\": \"990d22f3-86a5-4e9e-8afd-ac4c118896d4\",\r\n" + 
      	"        \"updated_at\": \"2016-08-14T05:55:45.250Z\",\r\n" + 
      	"        \"created_at\": \"2016-08-14T05:55:45.250Z\",\r\n" + 
      	"        \"name\": \"AdSquad2019\",\r\n" + 
      	"        \"campaign_id\": \"6cf25572-048b-4447-95d1-eb48231751be\",\r\n" + 
      	"        \"type\": \"SNAP_ADS\",\r\n" + 
      	"        \"placement\": \"SNAP_ADS\",\r\n" + 
      	"        \"optimization_goal\": \"IMPRESSIONS\",\r\n" + 
      	"        \"bid_micro\": 1000,\r\n" + 
      	"        \"daily_budget_micro\": 5555555\r\n" + 
      	"      }\r\n" + 
      	"    }\r\n" + 
      	"  ]\r\n" + 
      	"}";
  }// getSnapAdSquadUpdated()
  
  public static String getSnapSpecificAdSquad() {
      return "{\r\n" + 
      	"  \"request_status\": \"success\",\r\n" + 
      	"  \"request_id\": \"57b00bd200ff0a1092e59e2ec90001737e616473617069736300016275696c642d35396264653638322d312d31312d370001010d\",\r\n" + 
      	"  \"adsquads\": [\r\n" + 
      	"    {\r\n" + 
      	"      \"sub_request_status\": \"success\",\r\n" + 
      	"      \"adsquad\": {\r\n" + 
      	"        \"id\": \"23995202-bfbc-45a0-9702-dd6841af52fe\",\r\n" + 
      	"        \"updated_at\": \"2016-08-14T05:58:55.409Z\",\r\n" + 
      	"        \"created_at\": \"2016-08-14T05:58:55.409Z\",\r\n" + 
      	"	 \"end_time\": \"2016-08-16T05:58:55.409Z\",\r\n" +
      	"        \"name\": \"Ad Squad Uno\",\r\n" + 
      	"        \"status\": \"ACTIVE\",\r\n" + 
      	"        \"campaign_id\": \"6cf25572-048b-4447-95d1-eb48231751be\",\r\n" + 
      	"        \"type\": \"SNAP_ADS\",\r\n" + 
      	"        \"targeting\": {\r\n" + 
      	"          \"geos\": [\r\n" + 
      	"            {\r\n" + 
      	"              \"country_code\": \"us\"\r\n" + 
      	"            }\r\n" + 
      	"          ]\r\n" + 
      	"        },\r\n" + 
      	"        \"placement\": \"SNAP_ADS\",\r\n" + 
      	"        \"billing_event\": \"IMPRESSION\",\r\n" + 
      	"        \"bid_micro\": 1000000,\r\n" + 
      	"        \"daily_budget_micro\": 1000000000,\r\n" + 
      	"        \"start_time\": \"2016-08-11T22:03:58.869Z\",\r\n" + 
      	"        \"optimization_goal\": \"IMPRESSIONS\"\r\n" + 
      	"      }\r\n" + 
      	"    }\r\n" + 
      	"  ]\r\n" + 
      	"}";
  }// getSnapSpecificAdSquad()
  
  public static String getSnapAllAdSquadForCampaign() {
      return "{\r\n" + 
      	"  \"request_status\": \"success\",\r\n" + 
      	"  \"request_id\": \"57b00b1500ff0476bcfb96faa20001737e616473617069736300016275696c642d35396264653638322d312d31312d370001010b\",\r\n" + 
      	"  \"adsquads\": [\r\n" + 
      	"    {\r\n" + 
      	"      \"sub_request_status\": \"success\",\r\n" + 
      	"      \"adsquad\": {\r\n" + 
      	"        \"id\": \"0633e159-0f41-4675-a0ba-224fbd70ac4d\",\r\n" + 
      	"        \"updated_at\": \"2016-08-14T05:52:45.186Z\",\r\n" + 
      	"        \"created_at\": \"2016-08-14T05:52:45.186Z\",\r\n" + 
      	"        \"name\": \"Ad Squad Apples\",\r\n" + 
      	"        \"status\": \"PAUSED\",\r\n" + 
      	"        \"campaign_id\": \"6cf25572-048b-4447-95d1-eb48231751be\",\r\n" + 
      	"        \"type\": \"SNAP_ADS\",\r\n" + 
      	"        \"targeting\": {},\r\n" + 
      	"        \"placement\": \"SNAP_ADS\",\r\n" + 
      	"        \"billing_event\": \"IMPRESSION\",\r\n" + 
      	"        \"bid_micro\": 1000000,\r\n" + 
      	"        \"daily_budget_micro\": 1000000000,\r\n" + 
      	"        \"start_time\": \"2016-08-11T22:03:58.869Z\",\r\n" + 
      	"        \"optimization_goal\": \"IMPRESSIONS\"\r\n" + 
      	"      }\r\n" + 
      	"    },\r\n" + 
      	"    {\r\n" + 
      	"      \"sub_request_status\": \"success\",\r\n" + 
      	"      \"adsquad\": {\r\n" + 
      	"        \"id\": \"23995202-bfbc-45a0-9702-dd6841af52fe\",\r\n" + 
      	"        \"updated_at\": \"2016-08-14T05:58:55.409Z\",\r\n" + 
      	"        \"created_at\": \"2016-08-14T05:58:55.409Z\",\r\n" + 
      	"        \"name\": \"Ad Squad Uno\",\r\n" + 
      	"        \"status\": \"ACTIVE\",\r\n" + 
      	"        \"campaign_id\": \"6cf25572-048b-4447-95d1-eb48231751be\",\r\n" + 
      	"        \"type\": \"SNAP_ADS\",\r\n" + 
      	"        \"targeting\": {\r\n" + 
      	"          \"geos\": [\r\n" + 
      	"            {\r\n" + 
      	"              \"country_code\": \"us\"\r\n" + 
      	"            }\r\n" + 
      	"          ]\r\n" + 
      	"        },\r\n" + 
      	"        \"placement\": \"SNAP_ADS\",\r\n" + 
      	"        \"billing_event\": \"IMPRESSION\",\r\n" + 
      	"        \"bid_micro\": 1000000,\r\n" + 
      	"        \"daily_budget_micro\": 1000000000,\r\n" + 
      	"        \"start_time\": \"2016-08-11T22:03:58.869Z\",\r\n" + 
      	"        \"optimization_goal\": \"IMPRESSIONS\"\r\n" + 
      	"      }\r\n" + 
      	"    }\r\n" + 
      	"  ]\r\n" + 
      	"}";
  }// getSnapAllAdSquadForCampaign()
  
  public static String getSnapAllAdSquadForAdAccount() {
      return "{\r\n" + 
      	"  \"request_status\": \"success\",\r\n" + 
      	"  \"request_id\": \"57b00b1500ff0476bcfb96faa20001737e616473617069736300016275696c642d35396264653638322d312d31312d370001010b\",\r\n" + 
      	"  \"adsquads\": [\r\n" + 
      	"    {\r\n" + 
      	"      \"sub_request_status\": \"success\",\r\n" + 
      	"      \"adsquad\": {\r\n" + 
      	"        \"id\": \"0633e159-0f41-4675-a0ba-224fbd70ac4d\",\r\n" + 
      	"        \"updated_at\": \"2016-08-14T05:52:45.186Z\",\r\n" + 
      	"        \"created_at\": \"2016-08-14T05:52:45.186Z\",\r\n" + 
      	"        \"name\": \"Ad Squad Apples\",\r\n" + 
      	"        \"status\": \"PAUSED\",\r\n" + 
      	"        \"campaign_id\": \"6cf25572-048b-4447-95d1-eb48231751be\",\r\n" + 
      	"        \"type\": \"SNAP_ADS\",\r\n" + 
      	"        \"targeting\": {},\r\n" + 
      	"        \"placement\": \"SNAP_ADS\",\r\n" + 
      	"        \"billing_event\": \"IMPRESSION\",\r\n" + 
      	"        \"bid_micro\": 1000000,\r\n" + 
      	"        \"daily_budget_micro\": 1000000000,\r\n" + 
      	"        \"start_time\": \"2016-08-11T22:03:58.869Z\",\r\n" + 
      	"        \"optimization_goal\": \"IMPRESSIONS\"\r\n" + 
      	"      }\r\n" + 
      	"    },\r\n" + 
      	"    {\r\n" + 
      	"      \"sub_request_status\": \"success\",\r\n" + 
      	"      \"adsquad\": {\r\n" + 
      	"        \"id\": \"23995202-bfbc-45a0-9702-dd6841af52fe\",\r\n" + 
      	"        \"updated_at\": \"2016-08-14T05:58:55.409Z\",\r\n" + 
      	"        \"created_at\": \"2016-08-14T05:58:55.409Z\",\r\n" + 
      	"        \"name\": \"Ad Squad Uno\",\r\n" + 
      	"        \"status\": \"ACTIVE\",\r\n" + 
      	"        \"campaign_id\": \"6cf25572-048b-4447-95d1-eb48231751be\",\r\n" + 
      	"        \"type\": \"SNAP_ADS\",\r\n" + 
      	"        \"targeting\": {\r\n" + 
      	"          \"geos\": [\r\n" + 
      	"            {\r\n" + 
      	"              \"country_code\": \"us\"\r\n" + 
      	"            }\r\n" + 
      	"          ]\r\n" + 
      	"        },\r\n" + 
      	"        \"placement\": \"SNAP_ADS\",\r\n" + 
      	"        \"billing_event\": \"IMPRESSION\",\r\n" + 
      	"        \"bid_micro\": 1000000,\r\n" + 
      	"        \"daily_budget_micro\": 1000000000,\r\n" + 
      	"        \"start_time\": \"2016-08-11T22:03:58.869Z\",\r\n" + 
      	"        \"optimization_goal\": \"IMPRESSIONS\"\r\n" + 
      	"      }\r\n" + 
      	"    }\r\n" + 
      	"  ]\r\n" + 
      	"}";
  }// getSnapAllAdSquadForAdAccount()
  
  public static String getSnapAdCreated() {
     return "{\r\n" + 
     	"  \"request_status\": \"success\",\r\n" + 
     	"  \"request_id\": \"57b018c500ff0d22d3cefd730d0001737e616473617069736300016275696c642d35396264653638322d312d31312d3700010107\",\r\n" + 
     	"  \"ads\": [\r\n" + 
     	"    {\r\n" + 
     	"      \"sub_request_status\": \"success\",\r\n" + 
     	"      \"ad\": {\r\n" + 
     	"        \"id\": \"e8d6217f-32ab-400f-9e54-39a86a7963e4\",\r\n" + 
     	"        \"updated_at\": \"2016-08-14T07:07:50.241Z\",\r\n" + 
     	"        \"created_at\": \"2016-08-14T07:07:50.241Z\",\r\n" + 
     	"        \"name\": \"Ad One\",\r\n" + 
     	"        \"ad_squad_id\": \"23995202-bfbc-45a0-9702-dd6841af52fe\",\r\n" + 
     	"        \"creative_id\": \"c1e6e929-acec-466f-b023-852b8cacc18f\",\r\n" + 
     	"        \"status\": \"ACTIVE\",\r\n" + 
     	"        \"type\": \"SNAP_AD\"\r\n" + 
     	"      }\r\n" + 
     	"    }\r\n" + 
     	"  ]\r\n" + 
     	"}"; 
  }// getSnapAdCreated()
  
  public static String getSnapAdUpdated() {
      return "{\r\n" + 
      	"  \"request_status\": \"success\",\r\n" + 
      	"  \"request_id\": \"57b01a6300ff0c09d23a7706470001737e616473617069736300016275696c642d35396264653638322d312d31312d3700010108\",\r\n" + 
      	"  \"ads\": [\r\n" + 
      	"    {\r\n" + 
      	"      \"sub_request_status\": \"success\",\r\n" + 
      	"      \"ad\": {\r\n" + 
      	"        \"id\": \"e8d6217f-32ab-400f-9e54-39a86a7963e4\",\r\n" + 
      	"        \"updated_at\": \"2016-08-14T07:14:45.174Z\",\r\n" + 
      	"        \"created_at\": \"2016-08-14T07:07:50.241Z\",\r\n" + 
      	"        \"name\": \"Ad One\",\r\n" + 
      	"        \"ad_squad_id\": \"23995202-bfbc-45a0-9702-dd6841af52fe\",\r\n" + 
      	"        \"creative_id\": \"c1e6e929-acec-466f-b023-852b8cacc18f\",\r\n" + 
      	"        \"status\": \"ACTIVE\",\r\n" + 
      	"        \"type\": \"SNAP_AD\"\r\n" + 
      	"      }\r\n" + 
      	"    }\r\n" + 
      	"  ]\r\n" + 
      	"}";
  }// getSnapAdUpdated()
  
  public static String getSnapSpecificAd() { 
	  return "{\r\n" + 
  		"  \"request_status\": \"success\",\r\n" + 
  		"  \"request_id\": \"57b01adc00ff0a5290ad9d42c00001737e616473617069736300016275696c642d35396264653638322d312d31312d3700010103\",\r\n" + 
  		"  \"ads\": [\r\n" + 
  		"    {\r\n" + 
  		"      \"sub_request_status\": \"success\",\r\n" + 
  		"      \"ad\": {\r\n" + 
  		"        \"id\": \"e8d6217f-32ab-400f-9e54-39a86a7963e4\",\r\n" + 
  		"        \"updated_at\": \"2016-08-14T07:14:45.174Z\",\r\n" + 
  		"        \"created_at\": \"2016-08-14T07:07:50.241Z\",\r\n" + 
  		"        \"name\": \"Ad One\",\r\n" + 
  		"        \"ad_squad_id\": \"23995202-bfbc-45a0-9702-dd6841af52fe\",\r\n" + 
  		"        \"creative_id\": \"c1e6e929-acec-466f-b023-852b8cacc18f\",\r\n" + 
  		"        \"status\": \"ACTIVE\",\r\n" + 
  		"        \"type\": \"SNAP_AD\"\r\n" + 
  		"      }\r\n" + 
  		"    }\r\n" + 
  		"  ]\r\n" + 
  		"}"; 
  }// getSnapSpecificAd()
  
  public static String getSnapAllAdForAdSquad() {
	  return "{\r\n" + 
	  		"  \"request_status\": \"success\",\r\n" + 
	  		"  \"request_id\": \"57b01b3400ff0608a82948df6b0001737e616473617069736300016275696c642d35396264653638322d312d31312d370001010d\",\r\n" + 
	  		"  \"ads\": [\r\n" + 
	  		"    {\r\n" + 
	  		"      \"sub_request_status\": \"success\",\r\n" + 
	  		"      \"ad\": {\r\n" + 
	  		"        \"id\": \"2ded6d53-0805-4ff8-b984-54a7eb5c8918\",\r\n" + 
	  		"        \"updated_at\": \"2016-08-14T07:18:05.699Z\",\r\n" + 
	  		"        \"created_at\": \"2016-08-14T07:18:05.699Z\",\r\n" + 
	  		"        \"name\": \"Ad Two\",\r\n" + 
	  		"        \"ad_squad_id\": \"23995202-bfbc-45a0-9702-dd6841af52fe\",\r\n" + 
	  		"        \"creative_id\": \"c1e6e929-acec-466f-b023-852b8cacc18f\",\r\n" + 
	  		"        \"status\": \"PAUSED\",\r\n" + 
	  		"        \"type\": \"SNAP_AD\"\r\n" + 
	  		"      }\r\n" + 
	  		"    },\r\n" + 
	  		"    {\r\n" + 
	  		"      \"sub_request_status\": \"success\",\r\n" + 
	  		"      \"ad\": {\r\n" + 
	  		"        \"id\": \"e8d6217f-32ab-400f-9e54-39a86a7963e4\",\r\n" + 
	  		"        \"updated_at\": \"2016-08-14T07:14:45.174Z\",\r\n" + 
	  		"        \"created_at\": \"2016-08-14T07:07:50.241Z\",\r\n" + 
	  		"        \"name\": \"Ad One\",\r\n" + 
	  		"        \"ad_squad_id\": \"23995202-bfbc-45a0-9702-dd6841af52fe\",\r\n" + 
	  		"        \"creative_id\": \"c1e6e929-acec-466f-b023-852b8cacc18f\",\r\n" + 
	  		"        \"status\": \"ACTIVE\",\r\n" + 
	  		"        \"type\": \"SNAP_AD\"\r\n" + 
	  		"      }\r\n" + 
	  		"    }\r\n" + 
	  		"  ]\r\n" + 
	  		"}";
  }// getSnapAllAdForAdSquad()
  
  public static String getSnapAllAdForAdAccount() {
	  return "{\r\n" + 
	  		"  \"request_status\": \"success\",\r\n" + 
	  		"  \"request_id\": \"57b01b3400ff0608a82948df6b0001737e616473617069736300016275696c642d35396264653638322d312d31312d370001010d\",\r\n" + 
	  		"  \"ads\": [\r\n" + 
	  		"    {\r\n" + 
	  		"      \"sub_request_status\": \"success\",\r\n" + 
	  		"      \"ad\": {\r\n" + 
	  		"        \"id\": \"2ded6d53-0805-4ff8-b984-54a7eb5c8918\",\r\n" + 
	  		"        \"updated_at\": \"2016-08-14T07:18:05.699Z\",\r\n" + 
	  		"        \"created_at\": \"2016-08-14T07:18:05.699Z\",\r\n" + 
	  		"        \"name\": \"Ad Two\",\r\n" + 
	  		"        \"ad_squad_id\": \"23995202-bfbc-45a0-9702-dd6841af52fe\",\r\n" + 
	  		"        \"creative_id\": \"c1e6e929-acec-466f-b023-852b8cacc18f\",\r\n" + 
	  		"        \"status\": \"PAUSED\",\r\n" + 
	  		"        \"type\": \"SNAP_AD\"\r\n" + 
	  		"      }\r\n" + 
	  		"    },\r\n" + 
	  		"    {\r\n" + 
	  		"      \"sub_request_status\": \"success\",\r\n" + 
	  		"      \"ad\": {\r\n" + 
	  		"        \"id\": \"e8d6217f-32ab-400f-9e54-39a86a7963e4\",\r\n" + 
	  		"        \"updated_at\": \"2016-08-14T07:14:45.174Z\",\r\n" + 
	  		"        \"created_at\": \"2016-08-14T07:07:50.241Z\",\r\n" + 
	  		"        \"name\": \"Ad One\",\r\n" + 
	  		"        \"ad_squad_id\": \"23995202-bfbc-45a0-9702-dd6841af52fe\",\r\n" + 
	  		"        \"creative_id\": \"c1e6e929-acec-466f-b023-852b8cacc18f\",\r\n" + 
	  		"        \"status\": \"ACTIVE\",\r\n" + 
	  		"        \"type\": \"SNAP_AD\"\r\n" + 
	  		"      }\r\n" + 
	  		"    }\r\n" + 
	  		"  ]\r\n" + 
	  		"}";
  }// getSnapAllAdForAdAccount()
  
  public static String getSnapLargeMediaUploadMetaResponses() {
      return "{\r\n" + 
      	"    \"request_status\":\"SUCCESS\",\r\n" + 
      	"    \"request_id\":\"57e56c0b00ff0e1b43229b96350001737e616473617069736300016275696c642d62646338336131372d312d31352d3100010109\",\r\n" + 
      	"    \"upload_id\":\"cffc3975-f2b3-40d2-bf81-f0e7d97b9af5\",\r\n" + 
      	"    \"add_path\":\"/us/v1/media/7bd44f53-5de7-41c4-90c7-50633e5dbb7e/multipart-upload-v2?action=ADD\",\r\n" + 
      	"    \"finalize_path\":\"/v1/media/7bd44f53-5de7-41c4-90c7-50633e5dbb7e/multipart-upload-v2?action=FINALIZE\"\r\n" + 
      	"  }";
  }// getSnapLargeMediaUploadMetaResponses()
  
  public static String getSnapLargeMediaUpload() {
      return "{\r\n" + 
      	"  \"result\": {\r\n" + 
      	"    \"id\": \"7536bbc5-0074-4dc4-b654-5ba9cd9f9441\",\r\n" + 
      	"    \"updated_at\": \"2016-09-23T17:59:23.826Z\",\r\n" + 
      	"    \"created_at\": \"2016-09-23T17:51:07.799Z\",\r\n" + 
      	"    \"name\": \"Media Chunked - Video\",\r\n" + 
      	"    \"ad_account_id\": \"8adc3db7-8148-4fbf-999c-8d2266369d74\",\r\n" + 
      	"    \"type\": \"VIDEO\",\r\n" + 
      	"    \"media_status\": \"READY\",\r\n" + 
      	"    \"file_name\": \"lfv.mp4\",\r\n" + 
      	"    \"download_link\": \"https://storage.googleapis.com/ad-manager-creatives-production-us/7536bbc5-0074-4dc4-b654-5ba9cd9f9441/b195bd7c-aa7b-44c4-83a5-2c12e77a8784.mov\"\r\n" + 
      	"  },\r\n" + 
      	"  \"request_status\": \"SUCCESS\",\r\n" + 
      	"  \"request_id\": \"57e56d7a00ff0b82bd232ea3d40001737e616473617069736300016275696c642d62646338336131372d312d31352d3100010134\"\r\n" + 
      	"}";
  }// getSnapLargeMediaUpload()
  
  public static String getSnapAllMedia() {
      return "{\r\n" + 
      	"  \"request_status\": \"success\",\r\n" + 
      	"  \"request_id\": \"57b00ee800ff0a7e553a9389710001737e616473617069736300016275696c642d35396264653638322d312d31312d37000100\",\r\n" + 
      	"  \"media\": [\r\n" + 
      	"    {\r\n" + 
      	"      \"sub_request_status\": \"success\",\r\n" + 
      	"      \"media\": {\r\n" + 
      	"        \"id\": \"7f65f9ff-63d8-41e7-991a-06b95a1ffbde\",\r\n" + 
      	"        \"updated_at\": \"2016-08-12T20:39:57.029Z\",\r\n" + 
      	"        \"created_at\": \"2016-08-12T20:39:57.029Z\",\r\n" + 
      	"        \"name\": \"Media 2\",\r\n" + 
      	"        \"ad_account_id\": \"8adc3db7-8148-4fbf-999c-8d2266369d74\",\r\n" + 
      	"        \"type\": \"VIDEO\",\r\n" + 
      	"        \"media_status\": \"PENDING_UPLOAD\"\r\n" + 
      	"      }\r\n" + 
      	"    },\r\n" + 
      	"    {\r\n" + 
      	"      \"sub_request_status\": \"success\",\r\n" + 
      	"      \"media\": {\r\n" + 
      	"        \"id\": \"a7bee653-1865-41cf-8cee-8ab85a205837\",\r\n" + 
      	"        \"updated_at\": \"2016-08-14T06:24:28.378Z\",\r\n" + 
      	"        \"created_at\": \"2016-08-14T06:23:37.086Z\",\r\n" + 
      	"        \"name\": \"Media A - Video\",\r\n" + 
      	"        \"ad_account_id\": \"8adc3db7-8148-4fbf-999c-8d2266369d74\",\r\n" + 
      	"        \"type\": \"VIDEO\",\r\n" + 
      	"        \"media_status\": \"READY\",\r\n" + 
      	"        \"file_name\": \"sample.mov\"\r\n" + 
      	"      }\r\n" + 
      	"    },\r\n" + 
      	"    {\r\n" + 
      	"      \"sub_request_status\": \"success\",\r\n" + 
      	"      \"media\": {\r\n" + 
      	"        \"id\": \"ab32d7e5-1f80-4e1a-a76b-3c543d2b28e4\",\r\n" + 
      	"        \"updated_at\": \"2016-08-12T17:38:01.918Z\",\r\n" + 
      	"        \"created_at\": \"2016-08-12T17:36:59.740Z\",\r\n" + 
      	"        \"name\": \"App Icon\",\r\n" + 
      	"        \"ad_account_id\": \"8adc3db7-8148-4fbf-999c-8d2266369d74\",\r\n" + 
      	"        \"type\": \"IMAGE\",\r\n" + 
      	"        \"media_status\": \"READY\",\r\n" + 
      	"        \"file_name\": \"Mobile Strike.png\"\r\n" + 
      	"      }\r\n" + 
      	"    }\r\n" + 
      	"  ]\r\n" + 
      	"}";
  }// getSnapAllMedia()
  
  public static String getSnapMediaCreated() {
      return "{\r\n" + 
      	"  \"request_status\": \"success\",\r\n" + 
      	"  \"request_id\": \"57b00d1900ff08a0dae50ab27a0001737e616473617069736300016275696c642d35396264653638322d312d31312d370001010b\",\r\n" + 
      	"  \"media\": [\r\n" + 
      	"    {\r\n" + 
      	"      \"sub_request_status\": \"success\",\r\n" + 
      	"      \"media\": {\r\n" + 
      	"        \"id\": \"a7bee653-1865-41cf-8cee-8ab85a205837\",\r\n" + 
      	"        \"updated_at\": \"2016-08-14T06:18:01.855Z\",\r\n" + 
      	"        \"created_at\": \"2016-08-14T06:18:01.855Z\",\r\n" + 
      	"        \"name\": \"Media A - Video\",\r\n" + 
      	"        \"ad_account_id\": \"8adc3db7-8148-4fbf-999c-8d2266369d74\",\r\n" + 
      	"        \"type\": \"VIDEO\",\r\n" + 
      	"        \"media_status\": \"PENDING_UPLOAD\"\r\n" + 
      	"      }\r\n" + 
      	"    }\r\n" + 
      	"  ]\r\n" + 
      	"}";
  }// getSnapMediaCreated()
  
  public static String getSnapSpecificMedia() {
      return "{\r\n" + 
      	"  \"request_status\": \"success\",\r\n" + 
      	"  \"request_id\": \"57b00f5f00ff0bd6909e4148c30001737e616473617069736300016275696c642d35396264653638322d312d31312d3700010102\",\r\n" + 
      	"  \"media\": [\r\n" + 
      	"    {\r\n" + 
      	"      \"sub_request_status\": \"success\",\r\n" + 
      	"      \"media\": {\r\n" + 
      	"        \"id\": \"a7bee653-1865-41cf-8cee-8ab85a205837\",\r\n" + 
      	"        \"updated_at\": \"2016-08-14T06:24:28.378Z\",\r\n" + 
      	"        \"created_at\": \"2016-08-14T06:23:37.086Z\",\r\n" + 
      	"        \"name\": \"Media A - Video\",\r\n" + 
      	"        \"ad_account_id\": \"8adc3db7-8148-4fbf-999c-8d2266369d74\",\r\n" + 
      	"        \"type\": \"VIDEO\",\r\n" + 
      	"        \"media_status\": \"READY\",\r\n" + 
      	"        \"file_name\": \"sample.mov\"\r\n" + 
      	"      }\r\n" + 
      	"    }\r\n" + 
      	"  ]\r\n" + 
      	"}";
  }// getSnapSpecificMedia()
  
  public static String getSnapCreativeCreated() {
      return "{\r\n" + 
      	"  \"request_status\": \"success\",\r\n" + 
      	"  \"request_id\": \"57b0136f00ff085e3d63d358a20001737e616473617069736300016275696c642d35396264653638322d312d31312d3700010108\",\r\n" + 
      	"  \"creatives\": [\r\n" + 
      	"    {\r\n" + 
      	"      \"sub_request_status\": \"success\",\r\n" + 
      	"      \"creative\": {\r\n" + 
      	"        \"id\": \"c1e6e929-acec-466f-b023-852b8cacc18f\",\r\n" + 
      	"        \"updated_at\": \"2016-08-14T06:45:04.300Z\",\r\n" + 
      	"        \"created_at\": \"2016-08-14T06:45:04.300Z\",\r\n" + 
      	"        \"name\": \"Creative Creative\",\r\n" + 
      	"        \"brand_name\": \"Maxiumum Brand\",\r\n" + 
      	"        \"headline\": \"Healthy Living\",\r\n" + 
      	"        \"ad_account_id\": \"8adc3db7-8148-4fbf-999c-8d2266369d74\",\r\n" + 
      	"        \"type\": \"SNAP_AD\",\r\n" + 
      	"        \"packaging_status\": \"PENDING\",\r\n" + 
      	"        \"review_status\": \"PENDING_REVIEW\",\r\n" + 
      	"        \"shareable\": true,\r\n" + 
      	"        \"top_snap_media_id\": \"a7bee653-1865-41cf-8cee-8ab85a205837\",\r\n" + 
      	"        \"top_snap_crop_position\": \"MIDDLE\"\r\n" + 
      	"      }\r\n" + 
      	"    }\r\n" + 
      	"  ]\r\n" + 
      	"}";
  }// getSnapCreativeCreated()
  
  public static String getSnapCreativeUpdated() {
      return "{\r\n" + 
      	"    \"request_status\": \"SUCCESS\",\r\n" + 
      	"    \"request_id\": \"5c3008ed00ff04a89c4d5c66150001737e616473617069736300016275696c642d31326365666331382d312d3232362d3000000003\",\r\n" + 
      	"    \"creatives\": [\r\n" + 
      	"        {\r\n" + 
      	"            \"sub_request_status\": \"SUCCESS\",\r\n" + 
      	"            \"creative\": {\r\n" + 
      	"                \"id\": \"7772efab-028d-40ec-aa9d-7eb8f065c10a\",\r\n" + 
      	"                \"updated_at\": \"2019-01-11T21:21:50.991Z\",\r\n" + 
      	"                \"created_at\": \"2018-12-05T16:04:09.772Z\",\r\n" + 
      	"                \"created_by_app_id\": \"e4892f56-5303-4782-bfb2-14ebe02c6401\",\r\n" + 
      	"                \"created_by_user\": \"a22b5575-9052-437e-96c1-db781d62d203\",\r\n" + 
      	"                \"last_updated_by_app_id\": \"fffabe40-7639-4a6b-837d-b88f802dc3d2\",\r\n" + 
      	"                \"last_updated_by_user\": \"222b5575-9052-437e-96c1-db781d62d203\",\r\n" + 
      	"                \"name\": \"Citrus Creative\",\r\n" + 
      	"                \"ad_account_id\": \"8adc3db7-8148-4fbf-999c-8d2266369d74\",\r\n" + 
      	"                \"type\": \"WEB_VIEW\",\r\n" + 
      	"                \"packaging_status\": \"IN_PROGRESS\",\r\n" + 
      	"                \"review_status\": \"PENDING_REVIEW\",\r\n" + 
      	"                \"shareable\": true,\r\n" + 
      	"                \"headline\": \"For all Citrus Fans\",\r\n" + 
      	"                \"brand_name\": \"Example Inc\",\r\n" + 
      	"                \"call_to_action\": \"LISTEN\",\r\n" + 
      	"                \"top_snap_media_id\": \"647e6398-7e44-4ae5-a19d-c400b93bce32\",\r\n" + 
      	"                \"top_snap_crop_position\": \"MIDDLE\",\r\n" + 
      	"                \"web_view_properties\": {\r\n" + 
      	"                    \"url\": \"https://www.example.com/intro?utm_source=snapchat&utm_medium=paidsocial&utm_campaign=2019_citrus\",\r\n" + 
      	"                    \"allow_snap_javascript_sdk\": false,\r\n" + 
      	"                    \"use_immersive_mode\": false,\r\n" + 
      	"                    \"deep_link_urls\": [],\r\n" + 
      	"                    \"block_preload\": true\r\n" + 
      	"                },\r\n" + 
      	"                \"ad_product\": \"SNAP_AD\"\r\n" + 
      	"            }\r\n" + 
      	"        }\r\n" + 
      	"    ]\r\n" + 
      	"}";
  }// getSnapCreativeUpdated()
  
  public static String getSnapAllCreatives() {
      return "{\r\n" + 
      	"  \"request_status\": \"success\",\r\n" + 
      	"  \"request_id\": \"57b0171900ff0c73f50d5bd2f10001737e616473617069736300016275696c642d35396264653638322d312d31312d3700010106\",\r\n" + 
      	"  \"creatives\": [\r\n" + 
      	"    {\r\n" + 
      	"      \"sub_request_status\": \"success\",\r\n" + 
      	"      \"creative\": {\r\n" + 
      	"        \"id\": \"184fe3d0-ff80-4388-8d5f-05c340eff231\",\r\n" + 
      	"        \"updated_at\": \"2016-08-14T06:54:38.229Z\",\r\n" + 
      	"        \"created_at\": \"2016-08-14T06:54:38.229Z\",\r\n" + 
      	"        \"name\": \"Creative LFV\",\r\n" + 
      	"        \"ad_account_id\": \"8adc3db7-8148-4fbf-999c-8d2266369d74\",\r\n" + 
      	"        \"type\": \"LONGFORM_VIDEO\",\r\n" + 
      	"        \"packaging_status\": \"PENDING\",\r\n" + 
      	"        \"review_status\": \"PENDING_REVIEW\",\r\n" + 
      	"        \"shareable\": true,\r\n" + 
      	"        \"call_to_action\": \"WATCH\",\r\n" + 
      	"        \"top_snap_media_id\": \"a7bee653-1865-41cf-8cee-8ab85a205837\",\r\n" + 
      	"        \"top_snap_crop_position\": \"MIDDLE\",\r\n" + 
      	"        \"longform_video_properties\": {\r\n" + 
      	"          \"video_media_id\": \"a7bee653-1865-41cf-8cee-8ab85a205837\"\r\n" + 
      	"        }\r\n" + 
      	"      }\r\n" + 
      	"    },\r\n" + 
      	"    {\r\n" + 
      	"      \"sub_request_status\": \"success\",\r\n" + 
      	"      \"creative\": {\r\n" + 
      	"        \"id\": \"1c7065c2-ad9f-41cc-b2c5-d48d9810439b\",\r\n" + 
      	"        \"updated_at\": \"2016-08-14T06:56:48.631Z\",\r\n" + 
      	"        \"created_at\": \"2016-08-14T06:56:48.631Z\",\r\n" + 
      	"        \"name\": \"Creative App Install\",\r\n" + 
      	"        \"ad_account_id\": \"8adc3db7-8148-4fbf-999c-8d2266369d74\",\r\n" + 
      	"        \"type\": \"APP_INSTALL\",\r\n" + 
      	"        \"packaging_status\": \"PENDING\",\r\n" + 
      	"        \"review_status\": \"PENDING_REVIEW\",\r\n" + 
      	"        \"shareable\": true,\r\n" + 
      	"        \"call_to_action\": \"INSTALL_NOW\",\r\n" + 
      	"        \"top_snap_media_id\": \"a7bee653-1865-41cf-8cee-8ab85a205837\",\r\n" + 
      	"        \"top_snap_crop_position\": \"MIDDLE\",\r\n" + 
      	"        \"app_install_properties\": {\r\n" + 
      	"          \"app_name\": \"Cool App Yo\",\r\n" + 
      	"          \"ios_app_id\": \"447188370\",\r\n" + 
      	"          \"android_app_url\": \"com.snapchat.android\",\r\n" + 
      	"          \"icon_media_id\": \"ab32d7e5-1f80-4e1a-a76b-3c543d2b28e4\"\r\n" + 
      	"        }\r\n" + 
      	"      }\r\n" + 
      	"    },\r\n" + 
      	"    {\r\n" + 
      	"      \"sub_request_status\": \"success\",\r\n" + 
      	"      \"creative\": {\r\n" + 
      	"        \"id\": \"313e8415-6294-47d6-b064-5a0d9f21d224\",\r\n" + 
      	"        \"updated_at\": \"2016-08-14T06:54:52.107Z\",\r\n" + 
      	"        \"created_at\": \"2016-08-14T06:54:52.107Z\",\r\n" + 
      	"        \"name\": \"Creative LFV 2\",\r\n" + 
      	"        \"ad_account_id\": \"8adc3db7-8148-4fbf-999c-8d2266369d74\",\r\n" + 
      	"        \"type\": \"LONGFORM_VIDEO\",\r\n" + 
      	"        \"packaging_status\": \"PENDING\",\r\n" + 
      	"        \"review_status\": \"PENDING_REVIEW\",\r\n" + 
      	"        \"shareable\": true,\r\n" + 
      	"        \"call_to_action\": \"WATCH\",\r\n" + 
      	"        \"top_snap_media_id\": \"a7bee653-1865-41cf-8cee-8ab85a205837\",\r\n" + 
      	"        \"top_snap_crop_position\": \"MIDDLE\",\r\n" + 
      	"        \"longform_video_properties\": {\r\n" + 
      	"          \"video_media_id\": \"a7bee653-1865-41cf-8cee-8ab85a205837\"\r\n" + 
      	"        }\r\n" + 
      	"      }\r\n" + 
      	"    },\r\n" + 
      	"    {\r\n" + 
      	"      \"sub_request_status\": \"success\",\r\n" + 
      	"      \"creative\": {\r\n" + 
      	"        \"id\": \"67e4296c-486b-4bf3-877b-f34e8eeb173c\",\r\n" + 
      	"        \"updated_at\": \"2016-08-14T06:58:12.768Z\",\r\n" + 
      	"        \"created_at\": \"2016-08-14T06:58:12.768Z\",\r\n" + 
      	"        \"name\": \"Creative WV\",\r\n" + 
      	"        \"ad_account_id\": \"8adc3db7-8148-4fbf-999c-8d2266369d74\",\r\n" + 
      	"        \"type\": \"WEB_VIEW\",\r\n" + 
      	"        \"packaging_status\": \"PENDING\",\r\n" + 
      	"        \"review_status\": \"PENDING_REVIEW\",\r\n" + 
      	"        \"shareable\": true,\r\n" + 
      	"        \"call_to_action\": \"VIEW_MORE\",\r\n" + 
      	"        \"top_snap_media_id\": \"a7bee653-1865-41cf-8cee-8ab85a205837\",\r\n" + 
      	"        \"top_snap_crop_position\": \"MIDDLE\",\r\n" + 
      	"        \"web_view_properties\": {\r\n" + 
      	"          \"url\": \"http://snapchat.com/ads\"\r\n" + 
      	"        }\r\n" + 
      	"      }\r\n" + 
      	"    },\r\n" + 
      	"    {\r\n" + 
      	"      \"sub_request_status\": \"success\",\r\n" + 
      	"      \"creative\": {\r\n" + 
      	"        \"id\": \"c1e6e929-acec-466f-b023-852b8cacc18f\",\r\n" + 
      	"        \"updated_at\": \"2016-08-14T06:45:04.300Z\",\r\n" + 
      	"        \"created_at\": \"2016-08-14T06:45:04.300Z\",\r\n" + 
      	"        \"name\": \"Creative Creative\",\r\n" + 
      	"        \"ad_account_id\": \"8adc3db7-8148-4fbf-999c-8d2266369d74\",\r\n" + 
      	"        \"type\": \"SNAP_AD\",\r\n" + 
      	"        \"packaging_status\": \"PENDING\",\r\n" + 
      	"        \"review_status\": \"PENDING_REVIEW\",\r\n" + 
      	"        \"shareable\": true,\r\n" + 
      	"        \"top_snap_media_id\": \"a7bee653-1865-41cf-8cee-8ab85a205837\",\r\n" + 
      	"        \"top_snap_crop_position\": \"MIDDLE\"\r\n" + 
      	"      }\r\n" + 
      	"    }\r\n" + 
      	"  ]\r\n" + 
      	"}";
  }// getSnapAllCreatives()
  
  public static String getSnapPreviewMedia() {
      return "{\r\n" + 
      	"    \"request_status\": \"SUCCESS\",\r\n" + 
      	"    \"request_id\": \"5aa8450b00ff00ff741e879aff00580001737e816473617069736300016275696x642d37633033663563332d312d3134392d310001015e\",\r\n" + 
      	"    \"expires_at\": \"2018-03-14T21:39:23.303Z\",\r\n" + 
      	"    \"link\": \"https://adsapisc.appspot.com/media/video_preview?media_id=8e781365-ce4c-4336-8c53-f6a1f7c50af1&expires_at=1521063563303&signature=MGQCMA1DRI6uBax3GSq93E8hp5b3P2Ebg0lIlPa8iGML9rs1B9WFmPeHH6ttvx_rmDG5AgIwY0pjIvEEpwOXM8o3h9Hst60DjRN9Mw7am7OmkdrBGfoI4IiHBflv0XpK87Tnb_BE\"\r\n" + 
      	"}";
  }// getSnapPreviewMedia()
  
  public static String getSnapThumbnailMedia() {
      return "{\r\n" + 
      	"    \"request_status\": \"SUCCESS\",\r\n" + 
      	"    \"request_id\": \"5b99772a00ff06928d3e31e94b0001737e616473617069736300016275696c642d33346634346232622d312d3230302d3200010154\",\r\n" + 
      	"    \"expires_at\": \"2018-09-13T20:29:30.555Z\",\r\n" + 
      	"    \"link\": \"https://adsapisc.appspot.com/media/video_thumbnail?media_id=095a4a6d-01b0-4f6c-8901-41ee38c7b540&expires_at=1536870570555&signature=MGQCMBQ_NfJM0yZCrdyLiEon4Lkbei0zFJF2HpLiHa2NvSLV2JyOVhLqHfQgqbDWUuzaCQIwHzPj_ZFtPNk688SoFiKWUIFEKKBMhSm8t4moy9xlfgnoSv-8LMQ1omM_P8QCj7O9\"\r\n" + 
      	"}";
  }// getSnapThumbnailMedia()
  
  public static String getSnapSpecificCreative() {
      return "{\r\n" + 
      	"  \"request_status\": \"success\",\r\n" + 
      	"  \"request_id\": \"57b017a400ff057ca1dcf703da0001737e616473617069736300016275696c642d35396264653638322d312d31312d3700010101\",\r\n" + 
      	"  \"creatives\": [\r\n" + 
      	"    {\r\n" + 
      	"      \"sub_request_status\": \"success\",\r\n" + 
      	"      \"creative\": {\r\n" + 
      	"        \"id\": \"c1e6e929-acec-466f-b023-852b8cacc54f\",\r\n" + 
      	"        \"updated_at\": \"2016-08-14T06:45:04.300Z\",\r\n" + 
      	"        \"created_at\": \"2016-08-14T06:45:04.300Z\",\r\n" + 
      	"        \"name\": \"Creative Creative\",\r\n" + 
      	"        \"ad_account_id\": \"8adc3db7-8148-4fbf-999c-8d2266369d74\",\r\n" + 
      	"        \"type\": \"SNAP_AD\",\r\n" + 
      	"        \"packaging_status\": \"PENDING\",\r\n" + 
      	"        \"review_status\": \"PENDING_REVIEW\",\r\n" + 
      	"        \"shareable\": true,\r\n" + 
      	"        \"top_snap_media_id\": \"a7bee653-1865-41cf-8cee-8ab85a205837\",\r\n" + 
      	"        \"top_snap_crop_position\": \"MIDDLE\"\r\n" + 
      	"      }\r\n" + 
      	"    }\r\n" + 
      	"  ]\r\n" + 
      	"}";
  }// getSnapSpecificCreative()
  
  public static String getSnapPreviewCreative() {
      return "{\r\n" + 
      	"    \"request_status\": \"SUCCESS\",\r\n" + 
      	"    \"request_id\": \"5aa15e8000ff03acde535d9a200001737e616473617069736300016275696c642d62633562363761342d312d3134372d3600013326\",\r\n" + 
      	"    \"creative_id\": \"c1e6e929-acec-466f-b023-852b8cacc54f\",\r\n" + 
      	"    \"expires_at\": \"2018-03-09T15:51:33.248Z\",\r\n" + 
      	"    \"snapcode_link\": \"https://adsapisc.appspot.com/snapcodeimage/c1e6e929-acec-466f-b023-852b8cacc54f/352a899e-4a9d-3fdf-8efe-9bac9b8a0a21\"\r\n" + 
      	"}";
  }// getSnapPreviewCreative()
  
  public static String getSnapCreationCreativeElement() {
      return "{\r\n" + 
      	"    \"request_status\": \"SUCCESS\",\r\n" + 
      	"    \"request_id\": \"5bee332000ff06b565385cab550001737e616473617069736300016275696c642d33373039663463642d312d3232302d3000010145\",\r\n" + 
      	"    \"creative_elements\": [\r\n" + 
      	"        {\r\n" + 
      	"            \"sub_request_status\": \"SUCCESS\",\r\n" + 
      	"            \"creative_element\": {\r\n" + 
      	"                \"id\": \"f63bb5f5-471c-404f-8f0d-e5c1a003e4d9\",\r\n" + 
      	"                \"updated_at\": \"2018-11-16T03:01:52.907Z\",\r\n" + 
      	"                \"created_at\": \"2018-11-16T03:01:52.907Z\",\r\n" + 
      	"                \"name\": \"Product 1 button\",\r\n" + 
      	"                \"ad_account_id\": \"8adc3db7-8148-4fbf-999c-8d2266369d74\",\r\n" + 
      	"                \"type\": \"BUTTON\",\r\n" + 
      	"                \"interaction_type\": \"WEB_VIEW\",\r\n" + 
      	"                \"title\": \"Best title\",\r\n" + 
      	"                \"description\": \"Product 1\",\r\n" + 
      	"                \"button_properties\": {\r\n" + 
      	"                    \"button_overlay_media_id\": \"008a5ae9-bcc1-4c2e-a3f1-7e924d582019\"\r\n" + 
      	"                },\r\n" + 
      	"                \"web_view_properties\": {\r\n" + 
      	"                    \"url\": \"https://snapchat.com\",\r\n" + 
      	"                    \"allow_snap_javascript_sdk\": false,\r\n" + 
      	"                    \"use_immersive_mode\": false,\r\n" + 
      	"                    \"deep_link_urls\": [],\r\n" + 
      	"                    \"block_preload\": false\r\n" + 
      	"                }\r\n" + 
      	"            }\r\n" + 
      	"        }\r\n" + 
      	"    ]\r\n" + 
      	"}";
  }// getSnapCreationCreativeElement()
  
  public static String getSnapCreationCreativeElements() {
      return "{\r\n" + 
      	"    \"request_status\": \"SUCCESS\",\r\n" + 
      	"    \"request_id\": \"5bee33f200ff057a031dfee6af0001737e616473617069736300016275696c642d33373039663463642d312d3232302d300001011a\",\r\n" + 
      	"    \"creative_elements\": [\r\n" + 
      	"        {\r\n" + 
      	"            \"sub_request_status\": \"SUCCESS\",\r\n" + 
      	"            \"creative_element\": {\r\n" + 
      	"                \"id\": \"70debf44-cb4b-4b5f-8828-bd2b68b9f0ca\",\r\n" + 
      	"                \"updated_at\": \"2018-11-16T03:05:23.241Z\",\r\n" + 
      	"                \"created_at\": \"2018-11-16T03:05:23.241Z\",\r\n" + 
      	"                \"name\": \"Product 1 button\",\r\n" + 
      	"                \"ad_account_id\": \"8adc3db7-8148-4fbf-999c-8d2266369d74\",\r\n" + 
      	"                \"type\": \"BUTTON\",\r\n" + 
      	"                \"interaction_type\": \"WEB_VIEW\",\r\n" + 
      	"                \"title\": \"Best title\",\r\n" + 
      	"                \"description\": \"Product 1\",\r\n" + 
      	"                \"button_properties\": {\r\n" + 
      	"                    \"button_overlay_media_id\": \"008a5ae9-bcc1-4c2e-a3f1-7e924d582019\"\r\n" + 
      	"                },\r\n" + 
      	"                \"web_view_properties\": {\r\n" + 
      	"                    \"url\": \"https://snapchat.com\",\r\n" + 
      	"                    \"allow_snap_javascript_sdk\": false,\r\n" + 
      	"                    \"use_immersive_mode\": false,\r\n" + 
      	"                    \"deep_link_urls\": [],\r\n" + 
      	"                    \"block_preload\": false\r\n" + 
      	"                }\r\n" + 
      	"            }\r\n" + 
      	"        },\r\n" + 
      	"        {\r\n" + 
      	"            \"sub_request_status\": \"SUCCESS\",\r\n" + 
      	"            \"creative_element\": {\r\n" + 
      	"                \"id\": \"a2d1c8a0-0466-4924-b769-7a7e6ed5be3d\",\r\n" + 
      	"                \"updated_at\": \"2018-11-16T03:05:23.241Z\",\r\n" + 
      	"                \"created_at\": \"2018-11-16T03:05:23.241Z\",\r\n" + 
      	"                \"name\": \"Product 2 button\",\r\n" + 
      	"                \"ad_account_id\": \"8adc3db7-8148-4fbf-999c-8d2266369d74\",\r\n" + 
      	"                \"type\": \"BUTTON\",\r\n" + 
      	"                \"interaction_type\": \"WEB_VIEW\",\r\n" + 
      	"                \"title\": \"Best title\",\r\n" + 
      	"                \"description\": \"Product 2\",\r\n" + 
      	"                \"button_properties\": {\r\n" + 
      	"                    \"button_overlay_media_id\": \"008a5ae9-bcc1-4c2e-a3f1-7e924d582012\"\r\n" + 
      	"                },\r\n" + 
      	"                \"web_view_properties\": {\r\n" + 
      	"                    \"url\": \"https://snapchat2.com\",\r\n" + 
      	"                    \"allow_snap_javascript_sdk\": false,\r\n" + 
      	"                    \"use_immersive_mode\": false,\r\n" + 
      	"                    \"deep_link_urls\": [],\r\n" + 
      	"                    \"block_preload\": false\r\n" + 
      	"                }\r\n" + 
      	"            }\r\n" + 
      	"        },\r\n" + 
      	"        {\r\n" + 
      	"            \"sub_request_status\": \"SUCCESS\",\r\n" + 
      	"            \"creative_element\": {\r\n" + 
      	"                \"id\": \"4091233e-3351-405d-8684-a97e70c3b5da\",\r\n" + 
      	"                \"updated_at\": \"2018-11-16T03:05:23.242Z\",\r\n" + 
      	"                \"created_at\": \"2018-11-16T03:05:23.242Z\",\r\n" + 
      	"                \"name\": \"Product 3 button\",\r\n" + 
      	"                \"ad_account_id\": \"8adc3db7-8148-4fbf-999c-8d2266369d74\",\r\n" + 
      	"                \"type\": \"BUTTON\",\r\n" + 
      	"                \"interaction_type\": \"WEB_VIEW\",\r\n" + 
      	"                \"title\": \"Best title\",\r\n" + 
      	"                \"description\": \"Product 3\",\r\n" + 
      	"                \"button_properties\": {\r\n" + 
      	"                    \"button_overlay_media_id\": \"008a5ae9-bcc1-4c2e-a3f1-7e924d582013\"\r\n" + 
      	"                },\r\n" + 
      	"                \"web_view_properties\": {\r\n" + 
      	"                    \"url\": \"https://snapchat3.com\",\r\n" + 
      	"                    \"allow_snap_javascript_sdk\": false,\r\n" + 
      	"                    \"use_immersive_mode\": false,\r\n" + 
      	"                    \"deep_link_urls\": [],\r\n" + 
      	"                    \"block_preload\": false\r\n" + 
      	"                }\r\n" + 
      	"            }\r\n" + 
      	"        }\r\n" + 
      	"    ]\r\n" + 
      	"}";
  }// getSnapCreationCreativeElements()
  
  public static String getSnapCreationInteractionZone() {
      return "{\r\n" + 
      	"    \"request_status\": \"SUCCESS\",\r\n" + 
      	"    \"request_id\": \"5bee38df00ff00ff5858a22dbc9f0001737e616473617069736300016275696c642d33373039663463642d312d3232302d3000010154\",\r\n" + 
      	"    \"interaction_zones\": [\r\n" + 
      	"        {\r\n" + 
      	"            \"sub_request_status\": \"SUCCESS\",\r\n" + 
      	"            \"interaction_zone\": {\r\n" + 
      	"                \"id\": \"a218dc8b-7a79-4da6-9a1c-e5a581c7bd46\",\r\n" + 
      	"                \"updated_at\": \"2018-11-16T03:26:23.130Z\",\r\n" + 
      	"                \"created_at\": \"2018-11-16T03:26:23.130Z\",\r\n" + 
      	"                \"name\": \"First Interaction Zone\",\r\n" + 
      	"                \"ad_account_id\": \"8adc3db7-8148-4fbf-999c-8d2266369d74\",\r\n" + 
      	"                \"headline\": \"MORE\",\r\n" + 
      	"                \"creative_element_ids\": [\r\n" + 
      	"                    \"70debf44-cb4b-4b5f-8828-bd2b68b9f0cf\",\r\n" + 
      	"                    \"a2d1c8a0-0466-4924-b769-7a7e6ed5be3b\",\r\n" + 
      	"                    \"4091233e-3351-405d-8684-a97e70c3b5dc\",\r\n" + 
      	"                    \"f63bb5f5-471c-404f-8f0d-e5c1a003e4d9\"\r\n" + 
      	"                ]\r\n" + 
      	"            }\r\n" + 
      	"        }\r\n" + 
      	"    ]\r\n" + 
      	"}";
  }// getSnapCreationInteractionZone()
  
  public static String getSnapAudienceSegmentCreated() {
      return "{\r\n" + 
      	"  \"request_status\": \"success\",\r\n" + 
      	"  \"request_id\": \"57ae54d800ff0a4a5232b1a7210001737e616473617069736300016275696c642d35396264653638322d312d31312d37000100\",\r\n" + 
      	"  \"segments\": [\r\n" + 
      	"    {\r\n" + 
      	"      \"sub_request_status\": \"success\",\r\n" + 
      	"      \"segment\": {\r\n" + 
      	"        \"updated_at\": \"2016-08-12T22:59:42.452Z\",\r\n" + 
      	"        \"created_at\": \"2016-08-12T22:59:42.405Z\",\r\n" + 
      	"        \"name\": \"all the sams in the world\",\r\n" + 
      	"        \"id\": \"5677923948298240\",\r\n" + 
      	"        \"ad_account_id\": \"8adc3db7-8148-4fbf-999c-8d2266369d74\",\r\n" + 
      	"        \"description\": \"all the sams in the world\",\r\n" + 
      	"        \"status\": \"PENDING\",\r\n" + 
      	"        \"source_type\": \"FIRST_PARTY\",\r\n" + 
      	"        \"retention_in_days\": 180,\r\n" + 
      	"        \"approximate_number_users\": 0\r\n" + 
      	"      }\r\n" + 
      	"    }\r\n" + 
      	"  ]\r\n" + 
      	"}";
  }// getSnapAudienceSegmentCreated()
  
  public static String getSnapAudienceSegmentsCreated() {
      return "{\r\n" + 
      	"  \"request_status\": \"success\",\r\n" + 
      	"  \"request_id\": \"57ae554a00ff0487459c8ac85c0001737e616473617069736300016275696c642d34363138393265642d312d31312d3200010103\",\r\n" + 
      	"  \"segments\": [\r\n" + 
      	"    {\r\n" + 
      	"      \"sub_request_status\": \"success\",\r\n" + 
      	"      \"segment\": {\r\n" + 
      	"        \"updated_at\": \"2016-08-12T21:11:01.325Z\",\r\n" + 
      	"        \"created_at\": \"2016-08-12T21:11:01.196Z\",\r\n" + 
      	"        \"name\": \"super duper sam 2\",\r\n" + 
      	"        \"id\": \"5689640350646272\",\r\n" + 
      	"        \"ad_account_id\": \"8adc3db7-8148-4fbf-999c-8d2266369d74\",\r\n" + 
      	"        \"description\": \"all the sams in the world\",\r\n" + 
      	"        \"status\": \"PENDING\",\r\n" + 
      	"        \"source_type\": \"FIRST_PARTY\",\r\n" + 
      	"        \"retention_in_days\": 180,\r\n" + 
      	"        \"approximate_number_users\": 0\r\n" + 
      	"      }\r\n" + 
      	"    },\r\n" + 
      	"    {\r\n" + 
      	"      \"sub_request_status\": \"success\",\r\n" + 
      	"      \"segment\": {\r\n" + 
      	"        \"updated_at\": \"2016-08-12T20:58:16.098Z\",\r\n" + 
      	"        \"created_at\": \"2016-08-12T20:58:16.036Z\",\r\n" + 
      	"        \"name\": \"super duper sam\",\r\n" + 
      	"        \"id\": \"5715031928864768\",\r\n" + 
      	"        \"ad_account_id\": \"8adc3db7-8148-4fbf-999c-8d2266369d74\",\r\n" + 
      	"        \"description\": \"all the sams in the world\",\r\n" + 
      	"        \"status\": \"PENDING\",\r\n" + 
      	"        \"source_type\": \"FIRST_PARTY\",\r\n" + 
      	"        \"retention_in_days\": 180,\r\n" + 
      	"        \"approximate_number_users\": 0\r\n" + 
      	"      }\r\n" + 
      	"    }\r\n" + 
      	"  ]\r\n" + 
      	"}";
  }// getSnapAudienceSegmentsCreated()
  
  public static String getSnapSpecificAudienceSegment() {
      return "{\r\n" + 
      	"    \"request_status\": \"SUCCESS\",\r\n" + 
      	"    \"request_id\": \"5b3116c800ff0cfbb4758de4670001737e616473617069736300016275696c642d32636336303266382d312d3137382d300001015d\",\r\n" + 
      	"    \"segments\": [\r\n" + 
      	"        {\r\n" + 
      	"            \"sub_request_status\": \"SUCCESS\",\r\n" + 
      	"            \"segment\": {\r\n" + 
      	"                \"id\": \"5701023945457664\",\r\n" + 
      	"                \"updated_at\": \"2018-06-25T02:13:52.956Z\",\r\n" + 
      	"                \"created_at\": \"2018-03-09T00:57:57.462Z\",\r\n" + 
      	"                \"name\": \"Lookalike\",\r\n" + 
      	"                \"ad_account_id\": \"3f539865-c001-4f5e-bd31-5ae129a4550a\",\r\n" + 
      	"                \"status\": \"ACTIVE\",\r\n" + 
      	"                \"targetable_status\": \"READY\",\r\n" + 
      	"                \"upload_status\": \"COMPLETE\",\r\n" + 
      	"                \"source_type\": \"LOOKALIKE\",\r\n" + 
      	"                \"retention_in_days\": 180,\r\n" + 
      	"                \"approximate_number_users\": 11487000,\r\n" + 
      	"                \"creation_spec\": {\r\n" + 
      	"                    \"seed_segment_id\": \"5749764677173248\",\r\n" + 
      	"                    \"country\": \"US\",\r\n" + 
      	"                    \"type\": \"BALANCE\"\r\n" + 
      	"                }\r\n" + 
      	"            }\r\n" + 
      	"        }\r\n" + 
      	"    ]\r\n" + 
      	"}";
  }// getSnapSpecificAudienceSegment()
  
  public static String getSnapAudienceSegmentUpdated() {
      return "{\r\n" + 
      	"    \"request_status\": \"SUCCESS\",\r\n" + 
      	"    \"request_id\": \"5dc3096800ff0c266ebe6eeaac0001737e616473617069736300016275696c642d65653836646631392d312d3330322d300001013a\",\r\n" + 
      	"    \"segments\": [\r\n" + 
      	"        {\r\n" + 
      	"            \"sub_request_status\": \"SUCCESS\",\r\n" + 
      	"            \"segment\": {\r\n" + 
      	"                \"id\": \"5603670370513719\",\r\n" + 
      	"                \"updated_at\": \"2019-11-06T17:56:57.053Z\",\r\n" + 
      	"                \"created_at\": \"2019-03-28T14:47:17.956Z\",\r\n" + 
      	"                \"name\": \"Honey Bear Segment 2019\",\r\n" + 
      	"                \"ad_account_id\": \"22225ba6-7559-4000-9663-bace8adff5f1\",\r\n" + 
      	"                \"organization_id\": \"1fdeefec-f502-4ca8-9a84-6411e0a51052\",\r\n" + 
      	"                \"description\": \"A list of Honey bear lovers across the globe\",\r\n" + 
      	"                \"status\": \"PAUSED\",\r\n" + 
      	"                \"targetable_status\": \"TOO_FEW_USERS\",\r\n" + 
      	"                \"upload_status\": \"COMPLETE\",\r\n" + 
      	"                \"source_type\": \"FIRST_PARTY\",\r\n" + 
      	"                \"retention_in_days\": 60,\r\n" + 
      	"                \"approximate_number_users\": 500000,\r\n" + 
      	"                \"visible_to\": [\r\n" + 
      	"                    \"AdAccountEntity_22225ba6-7559-4000-9663-bace8adff5f1\"\r\n" + 
      	"                ]\r\n" + 
      	"            }\r\n" + 
      	"        }\r\n" + 
      	"    ]\r\n" + 
      	"}";
  }// getSnapAudienceSegmentUpdated()
  
  public static String getSnapAddUserForAudienceSegment() {
      return "{\r\n" + 
      	"  \"request_status\": \"SUCCESS\",\r\n" + 
      	"  \"request_id\": \"57c4d34700ff0d538b8ba40ed90001737e7465616d6b6f363139000173616d2d68616f6d696e672d757365722d746573740001010c\",\r\n" + 
      	"  \"users\": [\r\n" + 
      	"    {\r\n" + 
      	"      \"sub_request_status\": \"SUCCESS\",\r\n" + 
      	"      \"user\": {\r\n" + 
      	"        \"number_uploaded_users\": 2\r\n" + 
      	"      }\r\n" + 
      	"    }\r\n" + 
      	"  ]\r\n" + 
      	"}";
  }// getSnapAddUserForAudienceSegment()
  
  public static String getSnapDeleteUserForAudienceSegment() {
      return "{\r\n" + 
      	"  \"request_status\": \"SUCCESS\",\r\n" + 
      	"  \"request_id\": \"57c4d34700ff0d538b8ba40ed90001737e7465616d6b6f363139000173616d2d68616f6d696e672d757365722d746573740001010c\",\r\n" + 
      	"  \"users\": [\r\n" + 
      	"    {\r\n" + 
      	"      \"sub_request_status\": \"SUCCESS\",\r\n" + 
      	"      \"user\": {\r\n" + 
      	"        \"number_uploaded_users\": 2\r\n" + 
      	"      }\r\n" + 
      	"    }\r\n" + 
      	"  ]\r\n" + 
      	"}";
  }// getSnapDeleteUserForAudienceSegment()
  
  public static String getSnapDeleteAllUsersFromAudienceSegment() {
      return "{\r\n" + 
      	"  \"request_status\": \"SUCCESS\",\r\n" + 
      	"  \"request_id\": \"58af318900ff0cb66aa3a103dd0001737e616473617069736300016275696c642d61666139346564352d312d34342d3000010109\",\r\n" + 
      	"  \"segments\": [\r\n" + 
      	"    {\r\n" + 
      	"      \"sub_request_status\": \"SUCCESS\",\r\n" + 
      	"      \"segment\": {\r\n" + 
      	"        \"id\":\"5769345128988888\",\r\n" + 
      	"        \"updated_at\":\"2017-02-23T19:01:30.080Z\",\r\n" + 
      	"        \"created_at\":\"2017-02-23T18:34:48.900Z\",\r\n" + 
      	"        \"name\":\"super duper sam\",\r\n" + 
      	"        \"ad_account_id\":\"8adc3db7-8148-4fbf-999c-8d1111111d11\",\r\n" + 
      	"        \"description\":\"all the sams in the world\",\r\n" + 
      	"        \"status\":\"ACTIVE\",\r\n" + 
      	"        \"source_type\":\"FIRST_PARTY\",\r\n" + 
      	"        \"retention_in_days\":180,\r\n" + 
      	"        \"approximate_number_users\":0\r\n" + 
      	"      }\r\n" + 
      	"    }\r\n" + 
      	"  ]\r\n" + 
      	"}";
  }// getSnapDeleteAllUsersFromAudienceSegment()
  
  public static String getSnapDeleteAudienceSegment() {
      return "{\r\n" + 
      	"    \"request_status\": \"SUCCESS\",\r\n" + 
      	"    \"request_id\": \"5b994e4900ff02dce52463d1020001737e616473617069736300016275696c642d33346634346232622d312d3230302d32000100\",\r\n" + 
      	"    \"segments\": []\r\n" + 
      	"}";
  }// getSnapDeleteAudienceSegment()
} // SnapResponseUtils
