/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.mule.modules.keenio;

import org.mule.api.annotations.ConnectionStrategy;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.display.FriendlyName;
import org.mule.api.annotations.display.Placement;
import org.mule.api.annotations.param.Default;
import org.mule.modules.keenio.strategy.ConnectorConnectionStrategy;

import com.mulekeen.client.MuleKeenClient;

/**
 * Anypoint Connector
 *
 * @author Arunkumar Balachandran.
 */
@Connector(name="keen-io", friendlyName="KeenIO")
public class KeenIOConnector
{
    /**
     * Configurable
     */
    @Configurable
    @Default("value")
    private String myProperty;

    @ConnectionStrategy
    ConnectorConnectionStrategy connectionStrategy;

    /**
     * Custom processor
     *
     * {@sample.xml ../../../doc/keen-io-connector.xml.sample keen-io:my-processor}
     *
     * @param key This is the key for the keen event object
     * @param value This is the value for the keen event object
     * @param projectid This is the identifier for the project in keen
     * @param projectwrite This is the key for allowing to write to keen project 
     * @param projectread This is the key for allowing to read from keen project
     * @param ecollection This is the collection group to which events will be grouped
     * @return payload This is the combination of key and value of the keen event object.
     */
    @Processor(friendlyName="sendMessage")
    public String keenIOProcessor(@Placement(group="Event Details") @FriendlyName("Event key") String key,@Placement(group="Event Details") @FriendlyName("Event message") String value,
    	@Placement(group="Event Details") @FriendlyName("Collection name") String ecollection, 
    	@Placement(group="Connection Configuration") @FriendlyName("Project id") String projectid,@Placement(group="Connection Configuration") @FriendlyName("Write key") String projectwrite,
    	@Placement(group="Connection Configuration") @FriendlyName("Read key") String projectread) {
    	
    	System.out.println("keen.io connector starting..");
        MuleKeenClient client = new MuleKeenClient();
        client.sendEvent(projectid, projectwrite, projectread, key, value, ecollection);
        System.out.println("keen.io action completed");
        String payalod =  "{"+key+":"+value+"}";
        return payalod;

    }

    /**
     * Set property
     *
     * @param myProperty My property
     */
    public void setMyProperty(String myProperty) {
        this.myProperty = myProperty;
    }

    /**
     * Get property
     */
    public String getMyProperty() {
        return this.myProperty;
    }

    public ConnectorConnectionStrategy getConnectionStrategy() {
        return connectionStrategy;
    }

    public void setConnectionStrategy(ConnectorConnectionStrategy connectionStrategy) {
        this.connectionStrategy = connectionStrategy;
    }

}