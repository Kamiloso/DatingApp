package page.se3.datingapp.controller.old;

/*@Validated
@RequiredArgsConstructor
public class _MsgController {

    private final AuthService authService;
    private final MsgService msgService;

    @PostMapping("api/msg/send")
    public Message send(
            @AuthenticationPrincipal
            @NotNull
            Jwt jwt,

            @RequestBody
            @NotNull
            @Valid
            MsgSendRequest msgSendRequest

    ) throws UserNotFound, ChatNotFound, ChatAccessDenied, ReferencedResourceGone {
        User user = authService.getUser(jwt);
        return msgService.send(msgSendRequest, user.id());
    }

    @GetMapping("api/msg/fetch")
    public List<Message> fetch(
            @AuthenticationPrincipal
            @NotNull
            Jwt jwt,

            @RequestParam
            Long idChat,

            @RequestParam
            Instant lastFetchTime

    ) throws UserNotFound, ChatNotFound, ChatAccessDenied {
        User user = authService.getUser(jwt);
        return msgService.fetch(idChat, lastFetchTime, user.id());
    }
}*/
