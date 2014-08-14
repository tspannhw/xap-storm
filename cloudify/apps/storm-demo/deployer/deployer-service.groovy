/*******************************************************************************
* Copyright (c) 2012 GigaSpaces Technologies Ltd. All rights reserved
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*       http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*******************************************************************************/
import java.util.concurrent.TimeUnit;



service {

    name "deployer"
    type "APP_SERVER"
    icon "storm.png"
    numInstances 1


    compute {
        template "SMALL_LINUX"
    }

    lifecycle {

        install "deployer_install.groovy"
        start "deployer_start.groovy"

        locator {
            //hack to avoid monitoring started processes by cloudify
            return [] as LinkedList
        }


          }


}

