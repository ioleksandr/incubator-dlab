/*
 *
 *  * Copyright (c) 2018, EPAM SYSTEMS INC
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.epam.dlab.backendapi.schedulers;

import com.epam.dlab.backendapi.dao.BillingDAO;
import com.epam.dlab.backendapi.service.EnvironmentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.quartz.JobExecutionContext;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CheckApplicationQuoteSchedulerTest {

	@Mock
	private BillingDAO billingDAO;
	@Mock
	private EnvironmentService environmentService;
	@Mock
	private JobExecutionContext jobExecutionContext;
	@InjectMocks
	private CheckApplicationQuoteScheduler checkApplicationQuoteScheduler;

	@Test
	public void testWhenQuoteNotReached() {
		when(billingDAO.isBillingQuoteReached()).thenReturn(false);

		checkApplicationQuoteScheduler.execute(jobExecutionContext);

		verify(billingDAO).isBillingQuoteReached();
		verifyNoMoreInteractions(billingDAO);
		verifyZeroInteractions(environmentService);
	}

	@Test
	public void testWhenQuoteReached() {
		when(billingDAO.isBillingQuoteReached()).thenReturn(true);

		checkApplicationQuoteScheduler.execute(jobExecutionContext);

		verify(billingDAO).isBillingQuoteReached();
		verify(environmentService).stopAll();
		verifyNoMoreInteractions(billingDAO, environmentService);
	}
}