/*
 * Copyright (c) 2018, EPAM SYSTEMS INC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.epam.dlab.backendapi.service.gcp;

import com.epam.dlab.backendapi.SelfServiceApplicationConfiguration;
import com.epam.dlab.backendapi.resources.dto.gcp.GcpDataprocConfiguration;
import com.epam.dlab.backendapi.service.impl.InfrastructureTemplateServiceBase;
import com.epam.dlab.dto.base.computational.FullComputationalTemplate;
import com.epam.dlab.dto.imagemetadata.ComputationalMetadataDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.inject.Inject;

public class GcpInfrastructureTemplateService extends InfrastructureTemplateServiceBase {
	@Inject
	private SelfServiceApplicationConfiguration configuration;

	@Override
	protected FullComputationalTemplate getCloudFullComputationalTemplate(ComputationalMetadataDTO metadataDTO) {
		return new GcpFullComputationalTemplate(metadataDTO,
				GcpDataprocConfiguration.builder()
						.minInstanceCount(configuration.getMinInstanceCount())
						.maxInstanceCount(configuration.getMaxInstanceCount())
						.minDataprocPreemptibleInstanceCount(configuration.getMinDataprocPreemptibleCount())
						.build());
	}


	private class GcpFullComputationalTemplate extends FullComputationalTemplate {
		@JsonProperty("limits")
		private GcpDataprocConfiguration gcpDataprocConfiguration;

		GcpFullComputationalTemplate(ComputationalMetadataDTO metadataDTO,
									 GcpDataprocConfiguration gcpDataprocConfiguration) {
			super(metadataDTO);
			this.gcpDataprocConfiguration = gcpDataprocConfiguration;
		}
	}
}
