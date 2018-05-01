package in.fortrainer.admin.models;

/**
 * Created by Vivek on 5/1/2018.
 */

public class CreateSharedImage {
    private String id;

    private String code;

    private String url;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getCode ()
    {
        return code;
    }

    public void setCode (String code)
    {
        this.code = code;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", code = "+code+", url = "+url+"]";
    }
}
