import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

//Your folder here
def strSourceFolderpath = "c:\\inetpub\\wwwroot\\soilite";
def strDestFolderpath = "c:\\inetpub\\wwwroot\\";
def strOutputZipFileName = "test.zip";
  
File fileTopDir = new File(strSourceFolderpath);

//Parse the source folder. Get the root folder name and use that as the zip folder name
def strForlderParseRegex = '^(.*?)([^/]*?)(/(.[^/.]*))?$';

strSourceFolderpath = strSourceFolderpath.replace('\\', '/');
if(strSourceFolderpath.endsWith('/')){
    strSourceFolderpath += '/';
}
println strSourceFolderpath;

objMatcher = (strSourceFolderpath =~ strForlderParseRegex ); //$4 - will be the root folder
ZipEntry objZipEntry =  new ZipEntry(objMatcher[0][4]);

//Your zip objFile here
ZipOutputStream objZipOutput = new ZipOutputStream(new FileOutputStream(strDestFolderpath + '\\' + strOutputZipFileName));

int intTopDirLength = fileTopDir.absolutePath.length();

fileTopDir.eachFileRecurse
{ objFile ->
    def strRelative = objMatcher[0][4] + objFile.absolutePath.substring(intTopDirLength).replace('\\', '/');
   // objMatcher = ()
    
    if ( objFile.isDirectory() && !strRelative.endsWith('/'))
    {
        strRelative += "/";
    } 

    objZipEntry = new ZipEntry(strRelative);
    objZipEntry.time = objFile.lastModified();
    objZipOutput.putNextEntry(objZipEntry);

    if( objFile.isFile() )
    {
        objZipOutput << new FileInputStream(objFile);
    }
}

objZipOutput.close();