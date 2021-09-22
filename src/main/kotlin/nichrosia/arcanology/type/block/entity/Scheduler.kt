package nichrosia.arcanology.type.block.entity

interface Scheduler {
    val schedule: MutableList<Pair<Int, () -> Unit>>

    fun tick() {
        schedule.mapNotNull { (ticksRemaining, task) ->
            if (ticksRemaining == 0) {
                task(); null
            } else {
                (ticksRemaining - 1) to task
            }
        }
    }

    fun schedule(ticksUntilRun: Int, task: () -> Unit) {
        schedule.add(ticksUntilRun to task)
    }
}