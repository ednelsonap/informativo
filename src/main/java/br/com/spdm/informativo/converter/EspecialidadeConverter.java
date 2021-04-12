package br.com.spdm.informativo.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.spdm.informativo.model.Especialidade;

@FacesConverter(forClass = Especialidade.class)
public class EspecialidadeConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext faceContext, UIComponent uiComponent, String value) {
		if (value != null && !value.isEmpty()) {
            return (Especialidade) uiComponent.getAttributes().get(value);
        }
		return null;
	}

	@Override
	public String getAsString(FacesContext faceContext, UIComponent uiComponent, Object value) {
		if (value instanceof Especialidade) {
            Especialidade entity = (Especialidade) value;
            if (entity != null && entity instanceof Especialidade && entity.getId() != null) {
                uiComponent.getAttributes().put( entity.getId().toString(), entity);
                return entity.getId().toString();
            }
        }
		return "";
	}
	
}
