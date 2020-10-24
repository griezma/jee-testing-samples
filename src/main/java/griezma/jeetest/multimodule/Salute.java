package griezma.jeetest.multimodule;

import java.io.Serializable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Salute implements Serializable {
  
    private static final long serialVersionUID = 1L;

    @Getter
    private final String ID = String.valueOf(System.currentTimeMillis());

    @Getter
    private final String sender, message;
}