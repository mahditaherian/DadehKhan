package base.applicator.object;

import base.applicator.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mahdi
 */
public abstract class Entity {
    private List<Parameter> parameters = new ArrayList<>();
    
    public List<Parameter> getParameters() {
        return parameters;
    }
    
    protected void addParameter(Parameter parameter) {
        if (parameters.contains(parameter)) {
            parameters.remove(parameter);
        }
        parameters.add(parameter);
    }
    
    protected void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }
    
}
