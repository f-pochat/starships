package services.transformer

import factories.createBullet
import models.Bullet
import models.MovementVector
import models.Position
import services.GameService
import kotlin.math.cos
import kotlin.math.sin

class StarshipShooter(private val id: String) : Transformer {
    override fun transform(gameService: GameService): GameService {
        val starship = gameService.starships.find { it.starshipId == id }!!
        return gameService.copy(
            bullets = gameService.bullets + bullets(
                starship.bullets,
                starship.movementVector,
                starship.position,
                starship.damage
            )
        )
    }

    private fun bullets(amount: Int, movementVector: MovementVector, position: Position, damage: Double): List<Bullet> {
        return (1..amount).map {
            createBullet(
                bulletPosition(movementVector, position),
                damage,
                bulletRotation(it, amount, movementVector)
            )
        }
    }

    private fun bulletPosition(movementVector: MovementVector, position: Position): Position =
        Position(
            x = position.x + (20 * -sin(Math.toRadians(movementVector.rotation))),
            y = position.y + (20 * cos(Math.toRadians(movementVector.rotation)))
        )

    private fun bulletRotation(bulletNumber: Int, bulletAmount: Int, movementVector: MovementVector): Double {
        return 120 / (bulletAmount + 1) * bulletNumber + movementVector.rotation - 60
    }
}
