/*
 * Copyright 2017 Kyle Borowski
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
package com.sprinklercontrol.controller;

import com.sprinklercontrol.model.RxMsgStatus;
import com.sprinklercontrol.model.RxMsgZoneStatus;
import com.sprinklercontrol.model.RxMsgScheduleResponse;

/**
 *
 * @author kyle
 */
public interface CommTaskEventHandler {
    public void onStatus(RxMsgStatus msg);
    
    public void onZoneStatus(RxMsgZoneStatus msg);
    
    public void onScheduleQueryResponse(RxMsgScheduleResponse msg);
}
