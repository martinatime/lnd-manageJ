package de.cotto.lndmanagej.controller;

import com.codahale.metrics.MetricRegistry;
import de.cotto.lndmanagej.controller.dto.ChannelDetailsDto;
import de.cotto.lndmanagej.controller.dto.ObjectMapperConfiguration;
import de.cotto.lndmanagej.metrics.Metrics;
import de.cotto.lndmanagej.model.ChannelId;
import de.cotto.lndmanagej.model.LocalChannel;
import de.cotto.lndmanagej.model.Pubkey;
import de.cotto.lndmanagej.service.ChannelService;
import de.cotto.lndmanagej.service.NodeService;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/channel/{channelId}")
@Import(ObjectMapperConfiguration.class)
public class ChannelDetailsController {
    private final ChannelService channelService;
    private final NodeService nodeService;
    private final Metrics metrics;

    public ChannelDetailsController(ChannelService channelService, NodeService nodeService, Metrics metrics) {
        this.channelService = channelService;
        this.nodeService = nodeService;
        this.metrics = metrics;
    }

    @GetMapping("/details")
    public ChannelDetailsDto getDetails(@PathVariable ChannelId channelId) throws NotFoundException {
        metrics.mark(MetricRegistry.name(getClass(), "getDetails"));
        LocalChannel localChannel = channelService.getLocalChannel(channelId).orElse(null);
        if (localChannel == null) {
            throw new NotFoundException();
        }
        Pubkey remotePubkey = localChannel.getRemotePubkey();
        String remoteAlias = nodeService.getAlias(remotePubkey);
        boolean privateChannel = localChannel.isPrivateChannel();
        return new ChannelDetailsDto(localChannel.getId(), remotePubkey, remoteAlias, privateChannel);
    }
}