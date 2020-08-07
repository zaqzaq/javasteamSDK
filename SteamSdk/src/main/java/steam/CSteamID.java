/*
 * Copyright 2020 Nimbly Games, LLC
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

package steam;


public class CSteamID {
	final long uint64;
	final long m_EUniverse;
	final long m_EAccountType;
	final long m_unAccountInstance;
	final long m_unAccountID;
	
	public CSteamID(long uint64) {
		super();
		this.uint64 = uint64;
		m_EUniverse         = ((this.uint64 & 0xff00000000000000L) >>> 56) & 0xffL;			// 8 bits
		m_EAccountType      =  (this.uint64 & 0xf0000000000000L)   >>> 52;						// 4 bits
		m_unAccountInstance =  (this.uint64 & 0xfffff00000000L)    >>> 32;						// 20 bits
		m_unAccountID       =  (this.uint64 & 0x00000000ffffffffL) & 0xffffffffL;				// 32 bits
// erik test:
//		m_EUniverse         = (uint64 >> 56) & 0xFFL;	// 8 bits
//		m_EAccountType      = (uint64 >> 52) & 0xFL;	// 4 bits
//		m_unAccountInstance = (uint64 >> 32) & 0xFFFFFL;// 20 bits
//		m_unAccountID       = uint64 & 0xFFFFFFFFL;		// 32 bits
	}
	
	public long ConvertToUint64() {
		return uint64;
	}
	
	public EAccountType GetEAccountType(){
		return EAccountType.fromOrdinal((int)m_EAccountType);
	}
	
	public String toString() {
		return String.valueOf(uint64);
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (uint64 ^ (uint64 >>> 32));
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CSteamID other = (CSteamID) obj;
      return uint64 == other.uint64;
   }
}
