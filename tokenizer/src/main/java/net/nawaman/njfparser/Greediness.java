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

/**
 * Greediness specified how quantifier should be handled.
 *
 * @author Nawapunth Manusitthipol (https://github.com/NawaMan)
 */
public enum Greediness {
    
    /** Maximum possible match that still allow the later part to match: `+`. */
    Maximum,
    /** Minimum possible match that still allow the later part to match: `*`. */
    Minimum,
    /** Take all tokens as much as possible then check if found to be with in the range: `!`. */
    Obsessive,
    /** Take available tokens up to the upper bound and acceptable down to lower bound: ``. */
    Default
    ;
    
    /** Symbol for Maximum in RegParser language */
    public static final String MaximumSign   = "+";
    /** Symbol for Minimum in RegParser language */
    public static final String MinimumSign   = "*";
    /** Symbol for Obsessive in RegParser language */
    public static final String ObsessiveSign = "!";
    /** Symbol for Default in RegParser language */
    public static final String DefaultSign   = "";
    
    /** @return  <code>true</code>  if this is a maximum greediness */
    public boolean isMaximum() {
        return this == Maximum;
    }
    
    /** @return  <code>true</code>  if this is a minimum greediness. */
    public boolean isMinimum() {
        return this == Minimum;
    }
    
    /** @return  <code>true</code>  if this is a obsessive greediness. */
    public boolean isObsessive() {
        return this == Obsessive;
    }
    
    /** @return  <code>true</code>  if this is a default greediness. */
    public boolean isDefault() {
        return this == Default;
    }
    
    /** @return the sign of this greediness. */
    public String sign() {
        switch (this) {
            case Maximum:   return MaximumSign;
            case Minimum:   return MinimumSign;
            case Obsessive: return ObsessiveSign;
            case Default:   return DefaultSign;
            default:        throw new IllegalArgumentException("Unexpected greediness: " + this);
        }
    }
    
    /** @return  {@code true} if greediness is deterministic by itself. */
    public boolean isDeterministic() {
        switch (this) {
            case Maximum:   return false;
            case Minimum:   return false;
            case Obsessive: return true;
            case Default:   return true;
            default:        throw new IllegalArgumentException("Unexpected greediness: " + this);
        }
    }
    
}
