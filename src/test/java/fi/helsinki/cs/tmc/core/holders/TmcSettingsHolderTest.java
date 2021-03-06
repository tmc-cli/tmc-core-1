package fi.helsinki.cs.tmc.core.holders;

import static org.junit.Assert.assertEquals;

import fi.helsinki.cs.tmc.core.configuration.TmcSettings;
import fi.helsinki.cs.tmc.core.exceptions.UninitializedHolderException;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TmcSettingsHolderTest {

    private TmcSettingsHolder holder;

    @Mock private TmcSettings settings;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = UninitializedHolderException.class)
    public void throwsIfNotInitialized() {
        holder.set(null);
        holder.get();
    }

    @Test
    public void returnsCorrectAfterSet() {
        holder.set(settings);
        assertEquals(settings, holder.get());
    }
}
