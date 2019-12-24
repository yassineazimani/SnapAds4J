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
package snapads4j.model.creatives.elements;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import snapads4j.enums.CreativeTypeEnum;
import snapads4j.enums.InteractionTypeEnum;
import snapads4j.model.AbstractSnapModel;
import snapads4j.model.creatives.DeepLinkProperties;
import snapads4j.model.creatives.WebViewProperties;

/**
 * Creative Element
 *
 * @author Yassine AZIMANI
 */
@Getter
@Setter
@ToString
public class CreativeElement extends AbstractSnapModel {

    /**
     * Ad Account ID
     */
    @JsonProperty("ad_account_id")
    private String adAccountId;

    /**
     * Creative Element name
     */
    private String name;

    /**
     * Creative Element Type
     */
    private CreativeTypeEnum type;

    /**
     * Interaction Type
     */
    @JsonProperty("interaction_type")
    private InteractionTypeEnum interactionType;

    /**
     * Description
     */
    private String description;

    /**
     * Title
     */
    private String title;

    /**
     * Properties of the button like the image to be shown
     */
    @JsonProperty("button_properties")
    private ButtonProperties buttonProperties;

    /**
     * Properties of the web view to be used
     */
    @JsonProperty("web_view_properties")
    private WebViewProperties webViewProperties;

    /**
     * Properties of the deep link to be used
     */
    @JsonProperty("deep_link_properties")
    private DeepLinkProperties deepLinkProperties;

}// CreativeElement
