package com.pfchoice.springboot.security;

import org.springframework.security.access.expression.SecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

public class CustomWebSecurityExpressionHandler extends DefaultWebSecurityExpressionHandler {
    private static final AuthenticationTrustResolver TRUST_RESOLVER = new AuthenticationTrustResolverImpl();

    private String[] customInternalIpRanges;
    private String customTrustedProxyIpRange;

    @Override
    protected SecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication, FilterInvocation fi) {
    	CustomWebSecurityExpressionRoot root = new CustomWebSecurityExpressionRoot(authentication, fi);
        root.setPermissionEvaluator(getPermissionEvaluator());
        root.setTrustResolver(TRUST_RESOLVER);
        root.setRoleHierarchy(getRoleHierarchy());
       
        if (customInternalIpRanges != null) {
            root.setInternalIpRanges(customInternalIpRanges);
        }
        if (customTrustedProxyIpRange != null) {
            root.setTrustedProxyIpRange(customTrustedProxyIpRange);
        }

        return root;
    }

    /**
     * <p>Only set this if you want to override the default internal IP ranges defined within
     * {@link MyCompanyWebSecurityExpressionRoot}.</p>
     *
     * <p>See {@link MyCompanyWebSecurityExpressionRoot#setInternalIpRanges(String...)}</p>
     *
     * @param customInternalIpRanges ip address or ranges
     */
    public void setCustomInternalIpRanges(String... customInternalIpRanges) {
        this.customInternalIpRanges = customInternalIpRanges;
    }

    /**
     * Only set this if you want to override the default trusted proxy IP range set in
     * {@link MyCompanyWebSecurityExpressionRoot}.
     *
     * @param customTrustedProxyIpRange ip address or range
     */
    public void setCustomTrustedProxyIpRange(String customTrustedProxyIpRange) {
        this.customTrustedProxyIpRange = customTrustedProxyIpRange;
    }
}