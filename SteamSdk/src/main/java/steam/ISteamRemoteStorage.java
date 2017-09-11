package steam;

import java.nio.ByteBuffer;

/**
 * Filenames are case-insensitive, and will be converted to lowercase automatically.
 * So "foo.bar" and "Foo.bar" are the same file,
 * and if you write "Foo.bar" then iterate the files, the filename returned will be "foo.bar".
 *
 */
public class ISteamRemoteStorage {
	
	//	virtual bool	FileWrite( const char *pchFile, const void *pvData, int32 cubData ) = 0;
	public static boolean FileWrite(String pchFile, ByteBuffer pvData){
		return nFileWrite(pchFile, pvData);
	}
	private static native boolean nFileWrite(String pchFile, ByteBuffer pvData);
	
		
//	virtual int32	FileRead( const char *pchFile, void *pvData, int32 cubDataToRead ) = 0;
	/**
	 * This will read the file into the buffer pointed to by pvData, and returns the actual number of bytes read.
	 */
	public static int FileRead(String pchFile, ByteBuffer pvData){
		return nFileRead(pchFile, pvData);
	}
	private static native int nFileRead(String pchFile, ByteBuffer pvData);
	
	
//	virtual bool	FileForget( const char *pchFile ) = 0;
	/**
	 * There are two different APIs to delete a file, FileDelete and FileForget.</br>
	 * FileDelete is meant to be used when a user actively deletes a file.</br> 
	 * It deletes it from the local disk, and propagates that delete to the remote storage and any other machine the user uses.</br>
	 * FileForget is meant to be used when you are out of quota.</br>
	 * It deletes the file from remote storage, but leaves it on the local disk and is still accessible from the API.</br>
	 * When you are out of Cloud space, FileForget can be used to allow calls to FileWrite to keep working without needing to make the user delete files.</br>
	 * How you decide which files to forget are up to you.</br>
	 * It could be a simple LRU queue or something more complicated.</br>
	 * What's not recommended is leaving it up to the user. While it can be an option, requiring the user to manage their Cloud-ized files for a game - for instance, "Which file would you like to delete so that you may store this new one?" removes a significant advantage of using the Cloud in the first place: its transparency.
	 *
	 * Once a file has been deleted or forgotten, it can be re-written with FileWrite. Calling FileWrite on a forgotten file makes it persisted again. Currently, rewriting a forgotten file is the only way to make it persisted again.
	 */
	public static boolean FileForget(String pchFile){
		return nFileForget(pchFile);
	}
	
	private static native boolean nFileForget(String pchFile);
	
	
//	virtual bool	FileDelete( const char *pchFile ) = 0;
	/**
	 * There are two different APIs to delete a file, FileDelete and FileForget.</br>
	 * FileDelete is meant to be used when a user actively deletes a file.</br> 
	 * It deletes it from the local disk, and propagates that delete to the remote storage and any other machine the user uses.</br>
	 * FileForget is meant to be used when you are out of quota.</br>
	 * It deletes the file from remote storage, but leaves it on the local disk and is still accessible from the API.</br>
	 * When you are out of Cloud space, FileForget can be used to allow calls to FileWrite to keep working without needing to make the user delete files.</br>
	 * How you decide which files to forget are up to you.</br>
	 * It could be a simple LRU queue or something more complicated.</br>
	 * What's not recommended is leaving it up to the user. While it can be an option, requiring the user to manage their Cloud-ized files for a game - for instance, "Which file would you like to delete so that you may store this new one?" removes a significant advantage of using the Cloud in the first place: its transparency.
	 *
	 * Once a file has been deleted or forgotten, it can be re-written with FileWrite. Calling FileWrite on a forgotten file makes it persisted again. Currently, rewriting a forgotten file is the only way to make it persisted again.
	 */
	public static boolean FileDelete(String pchFile){
		return nFileDelete(pchFile);
	}
	private static native boolean nFileDelete(String pchFile);
	
//	virtual bool	FileExists( const char *pchFile ) = 0;
	public static boolean FileExists(String pchFile){
		return nFileExists(pchFile);
	}
	private static native boolean nFileExists(String pchFile);
	
	
//	virtual bool	FilePersisted( const char *pchFile ) = 0;
	public static boolean FilePersisted(String pchFile){
		return nFilePersisted(pchFile);
	}
	private static native boolean nFilePersisted(String pchFile);
	
	
//	virtual int32	GetFileSize( const char *pchFile ) = 0;
	public static int GetFileSize(String pchFile){
		return nGetFileSize(pchFile);
	}
	private static native int nGetFileSize(String pchFile);
	
	
//	virtual int64	GetFileTimestamp( const char *pchFile ) = 0;
	/**
	 * returns the file's last modified time in the Unix time - number of seconds from Jan 1, 1970.
	 */
	public static long GetFileTimestamp(String pchFile){
		return nGetFileTimestamp(pchFile);
	}
	private static native long nGetFileTimestamp(String pchFile);
	
	
//	virtual bool GetQuota( int32 *pnTotalBytes, int32 *puAvailableBytes ) = 0;
	public static int GetQuotaTotalBytes(){
		return nGetQuotaTotalBytes();
	}
	private static native int nGetQuotaTotalBytes();
	
	public static int GetQuotaAvailableBytes(){
		return nGetQuotaAvailableBytes();
	}
	private static native int nGetQuotaAvailableBytes();
	
	
//	virtual bool IsCloudEnabledForAccount() = 0;
	/**
	 * *Important* Even if synchronization is disabled all APIs will continue to work as normal.
	 * The only thing that won't happen is that Steam will not send files to or retrieve them from the Cloud storage.
	 * Do not keep duplicate copies of Cloud files and attempt to keep them in sync.
	 * Bugs in that kind of code often result in lost data.
	 */
	public static boolean IsCloudEnabledForAccount(){
		return nIsCloudEnabledForAccount();
	}
	private static native boolean nIsCloudEnabledForAccount();
	
	
//	virtual bool IsCloudEnabledForApp() = 0;
	/**
	 * *Important* Even if synchronization is disabled all APIs will continue to work as normal.
	 * The only thing that won't happen is that Steam will not send files to or retrieve them from the Cloud storage.
	 * Do not keep duplicate copies of Cloud files and attempt to keep them in sync.
	 * Bugs in that kind of code often result in lost data.
	 */
	public static boolean IsCloudEnabledForApp(){
		return nIsCloudEnabledForApp();
	}
	private static native boolean nIsCloudEnabledForApp();
	
	public static int GetFileCount(){
		return nGetFileCount();
	}
	private static native int nGetFileCount();
	
	public static SteamFileNameAndSize GetFileNameAndSize(int iFile){
		return nGetFileNameAndSize(iFile);
	}
	private static native SteamFileNameAndSize nGetFileNameAndSize(int iFile);
}
