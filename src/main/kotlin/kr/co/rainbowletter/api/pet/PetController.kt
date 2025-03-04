package kr.co.rainbowletter.api.pet

import io.swagger.v3.oas.annotations.Operation
import kr.co.rainbowletter.api.auth.RequireAuthentication
import kr.co.rainbowletter.api.auth.User
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/pets")
class PetController(
    private val petService: IPetService,
) {
    @GetMapping
    @RequireAuthentication
    @Operation(summary = "펫 조회")
    fun me(@AuthenticationPrincipal user: User): PetCollectResponse =
        PetCollectResponse(
            pets = petService.findByUserId(user.userEntity.id!!)
        )
}