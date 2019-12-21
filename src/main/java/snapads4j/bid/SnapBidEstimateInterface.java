package snapads4j.bid;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;

import snapads4j.exceptions.SnapArgumentException;
import snapads4j.exceptions.SnapExecutionException;
import snapads4j.exceptions.SnapOAuthAccessTokenException;
import snapads4j.exceptions.SnapResponseErrorException;
import snapads4j.model.bid.BidEstimate;
import snapads4j.model.bid.TargetingSpecBidEstimate;

public interface SnapBidEstimateInterface {

    Optional<BidEstimate> getBidEstimateBySquadSpec(String oAuthAccessToken, String adAccountID, TargetingSpecBidEstimate targetingSpecBidEstimate) throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
    JsonProcessingException, UnsupportedEncodingException, SnapExecutionException;
    
    Optional<BidEstimate> getBidEstimateByAdSquadId(String oAuthAccessToken, String adSquadID)throws SnapResponseErrorException, SnapOAuthAccessTokenException, SnapArgumentException,
    JsonProcessingException, UnsupportedEncodingException, SnapExecutionException;
    
}// SnapBidEstimateInterface
