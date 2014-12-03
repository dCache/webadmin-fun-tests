package org.dcache.webtests;

/**
 * Information about the remote dCache version.
 */
public class DcacheVersion
{
    private final int major;
    private final int minor;
    private final int bugfix;

    public static DcacheVersion parse(String descriptor) throws IllegalArgumentException
    {
        String[] element = descriptor.split("\\.");
        if (element.length != 3) {
            throw new IllegalArgumentException("Wrong number of elements: " +
                    descriptor);
        }

        int major = Integer.parseInt(element[0]);
        int minor = Integer.parseInt(element[1]);
        int bugfix;
        if (element[2].endsWith("-SNAPSHOT")) {
            bugfix = Integer.parseInt(element[2].substring(0, element [2].length()-9));
        } else {
            bugfix = Integer.parseInt(element[2]);
        }
        return new DcacheVersion(major, minor, bugfix);
    }

    public DcacheVersion(int major, int minor, int bugfix)
    {
        this.major = major;
        this.minor = minor;
        this.bugfix = bugfix;
    }

    public String toString()
    {
        return major + "." + minor + "." + bugfix;
    }

    public boolean isBefore(DcacheVersion other)
    {
        if (this.major != other.major) {
            return this.major < other.major;
        }
        if (this.minor != other.minor) {
            return this.minor < other.minor;
        }
        return this.bugfix < other.bugfix;
    }
}
