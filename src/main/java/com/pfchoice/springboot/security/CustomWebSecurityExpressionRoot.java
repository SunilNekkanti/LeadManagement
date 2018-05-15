package com.pfchoice.springboot.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.util.matcher.IpAddressMatcher;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.stream.Stream;

public class CustomWebSecurityExpressionRoot extends WebSecurityExpressionRoot {
    public static final String LOCALHOST = "127.0.0.1/32";

 //   public static final String COMPANY_DESKTOPS = "-suppressed for example-";
    public static final String COMPANY_INTERNET_1 = "108.190.27.18/32";
  //  public static final String COMPANY_INTERNET_2 = "-suppressed for example-";

    /**
     * See http://en.wikipedia.org/wiki/Private_network#Private_IPv4_address_spaces
     */
    public static final String RFC_1918_INTERNAL_A = "172.31.0.0/16";
    /**
     * See http://en.wikipedia.org/wiki/Private_network#Private_IPv4_address_spaces
     */
    public static final String RFC_1918_INTERNAL_B = "192.168.1.0/24";



    private IpAddressMatcher[] internalIpMatchers;
    private IpAddressMatcher trustedProxyMatcher;

    public CustomWebSecurityExpressionRoot(Authentication a, FilterInvocation fi) {
        super(a, fi);
        setInternalIpRanges(
                COMPANY_INTERNET_1,
   //             COMPANY_INTERNET_2,
   //             COMPANY_DESKTOPS,
                RFC_1918_INTERNAL_B,
                LOCALHOST);
        setTrustedProxyIpRange(RFC_1918_INTERNAL_A);
    }

    public boolean hasAnyIpAddress(String... ipAddresses) {
    	
    	String remoteIpAddressFinal; 
    	String remoteIpAddress = request.getHeader("X-FORWARDED-FOR");  
    	if (remoteIpAddress == null) {  
    		
    		WebAuthenticationDetails webAuthDetails = (WebAuthenticationDetails) authentication.getDetails();
    		remoteIpAddress = webAuthDetails.getRemoteAddress(); 
    	}
    	remoteIpAddressFinal =  remoteIpAddress.split(",")[0];
    	
        return Stream.of(ipAddresses)
                .anyMatch(ipAddress -> new IpAddressMatcher(ipAddress).matches(remoteIpAddressFinal));
    }

    public boolean hasAnyIpAddressBehindProxy(String trustedProxyRange, String... ipAddresses) {
    	
    	String remoteIpAddressFinal; 
    	String remoteIpAddress = request.getHeader("X-FORWARDED-FOR");  
    	if (remoteIpAddress == null) {  
    		
    		WebAuthenticationDetails webAuthDetails = (WebAuthenticationDetails) authentication.getDetails();
    		remoteIpAddress = webAuthDetails.getRemoteAddress(); 
    	}
    	remoteIpAddressFinal =  remoteIpAddress.split(",")[0];
    	
      //  String remoteIpAddress = getForwardedIp(trustedProxyRange).orElseGet(request::getRemoteAddr);
        return Stream.of(ipAddresses)
                        .anyMatch(ipAddress -> new IpAddressMatcher(ipAddress).matches(remoteIpAddressFinal));
    }

    public boolean isCompanyInternal() {
        String remoteIpAddress = getForwardedIp(trustedProxyMatcher).orElseGet(request::getRemoteAddr);
        return Stream.of(internalIpMatchers)
                .anyMatch(matcher -> matcher.matches(remoteIpAddress));
    }

    /**
     * <p>This specifies one or more IP addresses/ranges that indicate the remote client is from the company network.</p>
     *
     * <p>If not set, this will default to all of the following values:</p>
     *     <ul>
     *         <li>{@code RFC_1918_INTERNAL_A}</li>
     *         <li>{@code RFC_1918_INTERNAL_B}</li>
     *         <li>{@code RFC_1918_INTERNAL_C}</li>
     *         <li>{@code COMPANY_INTERNET_1}</li>
     *         <li>{@code COMPANY_INTERNET_2}</li>
     *         <li>{@code COMPANY_DESKTOPS}</li>
     *         <li>{@code LOCALHOST}</li>
     *     </ul>
     *
     * @param  internalIpRanges ip addresses or ranges. Must not be empty.
     *
     */
    public void setInternalIpRanges(String... internalIpRanges) {
        Assert.notEmpty(internalIpRanges, "At least one IP address/range is required");
        this.internalIpMatchers = Stream.of(internalIpRanges)
                .map(IpAddressMatcher::new)
                .toArray(IpAddressMatcher[]::new);
    }

    /**
     * <p>When checking for the <code>x-forwarded-for</code> header in the incoming request we will only use
     * that value from a trusted proxy as it can be spoofed by anyone. This value represents the IP address
     * or IP range that we will trust.</p>
     *
     * <p>The default value if this is not set is {@code RFC_1918_INTERNAL_A}.</p>
     *
     * @param  trustedProxyIpRange ip address or range. Must not be null.
     *
     */
    public void setTrustedProxyIpRange(String trustedProxyIpRange) {
        Assert.notNull(trustedProxyIpRange, "A non-null value is for trusted proxy IP address/range");
        this.trustedProxyMatcher = new IpAddressMatcher(trustedProxyIpRange);
    }

    /*private Optional<String> getForwardedIp(String trustedProxyRange) {
        return getForwardedIp(new IpAddressMatcher(trustedProxyRange));
    }*/

    private Optional<String> getForwardedIp(IpAddressMatcher trustedProxyMatcher) {
        String proxiedIp = request.getHeader("x-forwarded-for");
        if (proxiedIp != null && trustedProxyMatcher.matches(request.getRemoteAddr())) {
            return Optional.of(proxiedIp);
        }
        return Optional.empty();
    }

}