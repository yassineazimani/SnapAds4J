package snap.api.utils;

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

  public static String getSnapAllCampaigns() {
    return "{\r\n  \"request_status\": \"success\",\r\n  \"request_id\": \"57b003c700ff0f2e66c37f96c20001737e616473617069736300016275696c642d35396264653638322d312d31312d3700010103\",\r\n  \"campaigns\": [\r\n    {\r\n      \"sub_request_status\": \"success\",\r\n      \"campaign\": {\r\n        \"id\": \"06302efa-4c0f-4e36-b880-a395a36cef64\",\r\n        \"updated_at\": \"2016-08-12T20:28:58.738Z\",\r\n        \"created_at\": \"2016-08-12T20:28:58.738Z\",\r\n        \"name\": \"Campaign One\",\r\n        \"ad_account_id\": \"8adc3db7-8148-4fbf-999c-8d2266369d74\",\r\n        \"daily_budget_micro\": 200000000,\r\n        \"status\": \"ACTIVE\",\r\n        \"start_time\": \"2016-08-10T17:12:49.707Z\",\r\n        \"end_time\": \"2016-08-13T17:12:49.707Z\"\r\n      }\r\n    },\r\n    {\r\n      \"sub_request_status\": \"success\",\r\n      \"campaign\": {\r\n        \"id\": \"0fc8e179-6f3b-46e7-be8e-ca53fd404ece\",\r\n        \"updated_at\": \"2016-08-12T21:06:18.343Z\",\r\n        \"created_at\": \"2016-08-12T21:06:18.343Z\",\r\n        \"name\": \"Campaign Deux\",\r\n        \"ad_account_id\": \"8adc3db7-8148-4fbf-999c-8d2266369d74\",\r\n        \"daily_budget_micro\": 500000000,\r\n        \"status\": \"ACTIVE\",\r\n        \"start_time\": \"2016-08-10T17:12:49.707Z\",\r\n        \"end_time\": \"2016-08-13T17:12:49.707Z\"\r\n      }\r\n    },\r\n\r\n   \r\n\r\n    {\r\n      \"sub_request_status\": \"success\",\r\n      \"campaign\": {\r\n        \"id\": \"92e1c28a-a331-45b4-8c26-fd3e0eea8c39\",\r\n        \"updated_at\": \"2016-08-14T05:36:46.441Z\",\r\n        \"created_at\": \"2016-08-14T05:33:33.876Z\",\r\n        \"name\": \"Cool Campaign\",\r\n        \"ad_account_id\": \"8adc3db7-8148-4fbf-999c-8d2266369d74\",\r\n        \"status\": \"PAUSED\",\r\n        \"start_time\": \"2016-08-11T22:03:58.869Z\",\r\n        \"end_time\": \"2016-08-22T05:03:58.869Z\"\r\n      }\r\n    },\r\n    {\r\n      \"sub_request_status\": \"success\",\r\n      \"campaign\": {\r\n        \"id\": \"fedf8e04-0176-4ce3-a1ca-148204aee62c\",\r\n        \"updated_at\": \"2016-08-12T02:18:33.412Z\",\r\n        \"created_at\": \"2016-08-12T02:18:33.412Z\",\r\n        \"name\": \"Crazy Campaign\",\r\n        \"ad_account_id\": \"8adc3db7-8148-4fbf-999c-8d2266369d74\",\r\n        \"start_time\": \"2016-08-11T22:03:58.869Z\",\r\n        \"status\": \"PAUSED\"\r\n      }\r\n    }\r\n  ]\r\n}";
  } // getSnapAllCampaigns()

  public static String getSnapSpecificCampaign() {
    return "{\r\n  \"request_status\": \"success\",\r\n  \"request_id\": \"57b0049c00ff0e8cb21af5199c0001737e616473617069736300016275696c642d35396264653638322d312d31312d3700010107\",\r\n  \"campaigns\": [\r\n    {\r\n      \"sub_request_status\": \"success\",\r\n      \"campaign\": {\r\n        \"id\": \"92e1c28a-a331-45b4-8c26-fd3e0eea8c39\",\r\n        \"updated_at\": \"2016-08-14T05:36:46.441Z\",\r\n        \"created_at\": \"2016-08-14T05:33:33.876Z\",\r\n        \"name\": \"Cool Campaign\",\r\n        \"ad_account_id\": \"8adc3db7-8148-4fbf-999c-8d2266369d74\",\r\n        \"status\": \"PAUSED\",\r\n        \"start_time\": \"2016-08-11T22:03:58.869Z\",\r\n        \"end_time\": \"2016-08-22T05:03:58.869Z\"\r\n      }\r\n    }\r\n  ]\r\n}";
  } // getSnapSpecificCampaign()
  
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
} // SnapResponseUtils
