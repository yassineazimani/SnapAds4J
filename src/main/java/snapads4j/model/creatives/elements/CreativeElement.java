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

@Getter
@Setter
@ToString
public class CreativeElement extends AbstractSnapModel {

    @JsonProperty("ad_account_id")
    private String adAccountId;

    private String name;

    private CreativeTypeEnum type;

    @JsonProperty("interaction_type")
    private InteractionTypeEnum interactionType;

    private String description;

    private String title;

    @JsonProperty("button_properties")
    private ButtonProperties buttonProperties;

    @JsonProperty("web_view_properties")
    private WebViewProperties webViewProperties;

    @JsonProperty("deep_link_properties")
    private DeepLinkProperties deepLinkProperties;

}// CreativeElement
