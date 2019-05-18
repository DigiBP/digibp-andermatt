package ch.fhnw.justme.model;

public class SkillSuggestionsContext {
    private SuggestionsContext user_defined;

    public SkillSuggestionsContext() {
    }

    public SkillSuggestionsContext(SuggestionsContext user_defined) {
        this.user_defined = user_defined;
    }

    public SuggestionsContext getUser_defined() {
        return user_defined;
    }

    public void setUser_defined(SuggestionsContext user_defined) {
        this.user_defined = user_defined;
    }

    @Override
    public String toString() {
        return "SkillSuggestionsContext{" +
                "user_defined=" + user_defined +
                '}';
    }
}
