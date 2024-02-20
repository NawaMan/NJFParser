/*----------------------------------------------------------------------------------------------------------------------
 * Copyright (C) 2008-2022 Nawapunth Manusitthipol.
 *----------------------------------------------------------------------------------------------------------------------
 * LICENSE:
 * 
 * This file is part of Nawa's Java Functional Parser.
 * 
 * The project is a free software; you can redistribute it and/or modify it under the SIMILAR terms of the GNU General
 * Public License as published by the Free Software Foundation; either version 2 of the License, or any later version.
 * You are only required to inform me about your modification and redistribution as or as part of commercial software
 * package. You can inform me via nawa<at>nawaman<dot>net.
 * 
 * The project is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the 
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for
 * more details.
 * ---------------------------------------------------------------------------------------------------------------------
 */
package net.nawaman.njfparser;

import static java.lang.String.format;
import static net.nawaman.njfparser.Greediness.Default;
import static net.nawaman.njfparser.Greediness.Maximum;
import static net.nawaman.njfparser.Greediness.Minimum;
import static net.nawaman.njfparser.Greediness.Obsessive;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Quantifier for matching
 *
 * @author Nawapunth Manusitthipol (https://github.com/NawaMan)
 */
public class Quantifier implements Serializable {
    
    private static final long serialVersionUID = 1930305369240858722L;
    
    /** Predefine Quantifier for Zero */
    public static final Quantifier Zero       = new Quantifier(0, 0, Default);
    /** Predefine Quantifier for One */
    public static final Quantifier One        = new Quantifier(1, 1, Default);
    /** Predefine Quantifier for ZeroOrOne */
    public static final Quantifier ZeroOrOne  = new Quantifier(0, 1, Default);
    /** Predefine Quantifier for ZeroOrMore */
    public static final Quantifier ZeroOrMore = new Quantifier(0, Default);
    /** Predefine Quantifier for OneOrMore */
    public static final Quantifier OneOrMore  = new Quantifier(1, Default);
    
    /** Predefine Quantifier for ZeroOrOne */
    public static final Quantifier ZeroOrOne_Maximum     = new Quantifier(0, 1, Maximum);
    /** Predefine Quantifier for ZeroOrMore */
    public static final Quantifier ZeroOrMore_Maximum    = new Quantifier(0, Maximum);
    /** Predefine Quantifier for OneOrMore */
    public static final Quantifier OneOrMore_Maximum     = new Quantifier(1, Maximum);
    /** Predefine Quantifier for ZeroOrOne */
    public static final Quantifier ZeroOrOne_Minimum     = new Quantifier(0, 1, Minimum);
    /** Predefine Quantifier for ZeroOrMore */
    public static final Quantifier ZeroOrMore_Minimum    = new Quantifier(0, Minimum);
    /** Predefine Quantifier for OneOrMore */
    public static final Quantifier OneOrMore_Minimum     = new Quantifier(1, Minimum);
    /** Predefine Quantifier for ZeroOrOne */
    public static final Quantifier ZeroOrOne_Default  = ZeroOrOne;
    /** Predefine Quantifier for ZeroOrMore */
    public static final Quantifier ZeroOrMore_Default = ZeroOrMore;
    /** Predefine Quantifier for OneOrMore */
    public static final Quantifier OneOrMore_Default  = OneOrMore;
    
    /** Predefine Quantifier for None */
    public static final Quantifier None = new Quantifier(0, Obsessive);
    
    /** The upper bound value for no upper bound. */
    public static final int NO_UPPERBOUND = -1;
    
    /**
     * Create a Quantifier for a bound.
     * 
     * @param  bound  the bound (both lower and upper).
     * @return        the quantifier.
     */
    public static Quantifier bound(int bound) {
        return new Quantifier(bound, bound);
    }
    
    /**
     * Create a Quantifier for a bound.
     * 
     * @param  lowerBound  the lower bound.
     * @param  upperBound  the upper bound.
     * @return             the quantifier.
     */
    public static Quantifier bound(int lowerBound, int upperBound) {
        return new Quantifier(lowerBound, upperBound);
    }
    
    /**
     * Returns toString for the quantifier or empty string if the given value is null.
     * 
     * @param quantifier  the quantifier.
     * @return            the toString for the quantifier or empty string if the given value is null.
     */
    public static String toString(Quantifier quantifier) {
        return (quantifier == null) ? "" : quantifier.toString();
    }
    
    private final int        lowerBound;
    private final int        upperBound;
    private final Greediness greediness;
    
    
    private transient AtomicReference<String> toString = new AtomicReference<>(null);
    
    
    /**
     * Constructs a {@link Quantifier}.
     * 
     * @param lowerBound  the lower bound.
     * @param greediness  the greediness.
     */
    public Quantifier(int lowerBound, Greediness greediness) {
        this(lowerBound, -1, greediness);
    }
    
    /**
     * Constructs a {@link Quantifier} with default greediness.
     * 
     * @param lowerBound  the lower bound.
     * @param upperBound  the upper bound.
     */
    public Quantifier(int lowerBound, int upperBound) {
        this(lowerBound, upperBound, null);
    }
    
    /**
     * Constructs a {@link Quantifier}.
     * 
     * @param lowerBound  the lower bound.
     * @param upperBound  the upper bound.
     * @param greediness  the greediness.
     */
    public Quantifier(int lowerBound, int upperBound, Greediness greediness) {
        if ((lowerBound < 0) || ((upperBound >= 0) && (lowerBound > upperBound))) {
            var errMsg = format("Invalid qualifier: lowerBound=[%d] upperBound=[%d]", lowerBound, upperBound);
            throw new IllegalArgumentException(errMsg);
        }
        
        if (upperBound < 0) {
            upperBound = -1;
        }
        
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.greediness = (greediness != null) ? greediness : Default;
    }
    
    /** @return  <code>true</code> if this Quantifier is a one and possessive */
    public boolean isOne_Default() {
        return isOne() && isDefault();
    }
    
    /** @return  <code>true</code> if this Quantifier is a zero */
    public boolean isZero() {
        return (lowerBound == 0) && (upperBound == 0);
    }
    
    /** @return  <code>true</code> if this Quantifier is a one */
    public boolean isOne() {
        return (lowerBound == 1) && (upperBound == 1);
    }
    
    /** @return  <code>true</code> if this Quantifier is a zero or one */
    public boolean isZeroOrOne() {
        return (lowerBound == 0) && (upperBound == 1);
    }
    
    /** @return  <code>true</code> if this Quantifier is a zero or more */
    public boolean isZeroOrMore() {
        return (lowerBound == 0) && (upperBound == -1);
    }
    
    /** @return  <code>true</code> if this Quantifier is a one or more */
    public boolean isOneOrMore() {
        return (lowerBound == 1) && (upperBound == -1);
    }
    
    /** @return  the lower bound */
    public int lowerBound() {
        return lowerBound;
    }
    
    /** @return  the upper bound (-1 for unlimited) */
    public int upperBound() {
        return upperBound;
    }
    
    /** @return  <code>true</code> if this quantifier has no upper bound */
    public boolean hasNoUpperBound() {
        return upperBound == NO_UPPERBOUND;
    }
    
    /** @return  <code>true</code> if this quantifier has no upper bound */
    public boolean hasUpperBound() {
        return upperBound != NO_UPPERBOUND;
    }
    
    /** @return  how greedy this qualifier */
    public Greediness greediness() {
        return greediness;
    }
    
    /** @return  <code>true</code> if this is a maximum greediness */
    public boolean isMaximum() {
        return greediness == Maximum;
    }
    
    /** @return  <code>true</code> if this is a minimum greediness */
    public boolean isMinimum() {
        return greediness == Minimum;
    }
    
    /** @return  <code>true</code> if this is a obsessive greediness */
    public boolean isObsessive() {
        return greediness == Obsessive;
    }
    
    /** @return  <code>true</code> if this is a default greediness */
    public boolean isDefault() {
        return greediness == Default;
    }
    
    /** @return  a quantifier with this lower/upper bound but with maximum greediness. */
    public Quantifier withMaximum() {
        return (greediness == Maximum) ? this : new Quantifier(lowerBound, upperBound, Maximum);
    }
    
    /** @return  a quantifier with this lower/upper bound but with minimum greediness. */
    public Quantifier withMinimum() {
        return (greediness == Minimum) ? this : new Quantifier(lowerBound, upperBound, Minimum);
    }
    
    /** @return  a quantifier with this lower/upper bound but with obsessive greediness. */
    public Quantifier withObsessive() {
        return (greediness == Obsessive) ? this : new Quantifier(lowerBound, upperBound, Obsessive);
    }
    
    /** @return  a quantifier with this lower/upper bound but with default greediness. */
    public Quantifier withDefault() {
        return (greediness == Default) ? this : new Quantifier(lowerBound, upperBound, Default);
    }
    
    /** @return  the string representation of the qualifier */
    @Override
    public String toString() {
        if (toString == null) {
            // This can happens if he object was deserialize.
            toString = new AtomicReference<>(null);
        }
        return toString.updateAndGet(oldValue -> {
            if (oldValue != null)
                return oldValue;
            
            if (isZeroOrOne())
                return "?" + greediness.sign();
            
            if (isZeroOrMore())
                return "*" + greediness.sign();
            
            if (isOneOrMore())
                return "+" + greediness.sign();
            
            if (isZero())
                return "{0}";
            
            if (isOne())
                return "" + greediness.sign();
            
            if (lowerBound == upperBound)
                return "{" + lowerBound + "}" + greediness.sign();
            
            if (lowerBound == 0)
                return "{," + upperBound + "}" + greediness.sign();
            
            if (upperBound == -1)
                return "{" + lowerBound + ",}" + greediness.sign();
            
            return "{" + lowerBound + "," + upperBound + "}" + greediness.sign();
        });
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(greediness, lowerBound, toString, upperBound);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        
        if (obj == null)
            return false;
        
        if (getClass() != obj.getClass())
            return false;
        
        var other = (Quantifier)obj;
        return greediness == other.greediness
            && lowerBound == other.lowerBound
            && upperBound == other.upperBound;
    }
    
}
