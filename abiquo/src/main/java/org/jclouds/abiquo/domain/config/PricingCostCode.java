/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jclouds.abiquo.domain.config;

import static com.google.common.base.Preconditions.checkNotNull;

import java.math.BigDecimal;

import org.jclouds.abiquo.AbiquoApi;
import org.jclouds.abiquo.domain.DomainWrapper;
import org.jclouds.abiquo.reference.ValidationErrors;
import org.jclouds.rest.ApiContext;

import com.abiquo.model.rest.RESTLink;
import com.abiquo.server.core.pricing.PricingCostCodeDto;

/**
 * Associates a {@link CostCode} with a pricing template.
 * 
 * @author Susana Acedo
 */
public class PricingCostCode extends DomainWrapper<PricingCostCodeDto> {
   private CostCode costcode;

   private PricingTemplate pricingTemplate;

   protected PricingCostCode(final ApiContext<AbiquoApi> context, final PricingCostCodeDto target) {
      super(context, target);
   }

   // Builder

   public static Builder builder(final ApiContext<AbiquoApi> context, final PricingTemplate pricingtemplate,
         final CostCode costcode) {
      return new Builder(context, pricingtemplate, costcode);
   }

   public static class Builder {
      private ApiContext<AbiquoApi> context;

      private Integer id;

      private PricingTemplate pricingTemplate;

      private CostCode costcode;

      private BigDecimal price;

      public Builder(final ApiContext<AbiquoApi> context, final PricingTemplate pricingTemplate, final CostCode costcode) {
         super();
         this.pricingTemplate = checkNotNull(pricingTemplate, ValidationErrors.NULL_RESOURCE + PricingTemplate.class);
         this.costcode = checkNotNull(costcode, ValidationErrors.NULL_RESOURCE + CostCode.class);
         this.context = context;
      }

      public Builder price(final BigDecimal price) {
         this.price = price;
         return this;
      }

      public PricingCostCode build() {
         PricingCostCodeDto dto = new PricingCostCodeDto();
         dto.setId(id);
         dto.setPrice(price);

         RESTLink link = costcode.unwrap().searchLink("edit");
         checkNotNull(link, ValidationErrors.MISSING_REQUIRED_LINK);
         dto.addLink(new RESTLink("costcode", link.getHref()));

         PricingCostCode pricingcostcode = new PricingCostCode(context, dto);
         pricingcostcode.pricingTemplate = pricingTemplate;
         pricingcostcode.costcode = costcode;

         return pricingcostcode;
      }

      public static Builder fromPricingCostCode(final PricingCostCode in) {
         return PricingCostCode.builder(in.context, in.pricingTemplate, in.costcode).price(in.getPrice());
      }
   }

   // Delegate methods

   public Integer getId() {
      return target.getId();
   }

   public BigDecimal getPrice() {
      return target.getPrice();
   }

   @Override
   public String toString() {
      return "PricingCostCode [id=" + getId() + ", price=" + getPrice() + "]";
   }

}
