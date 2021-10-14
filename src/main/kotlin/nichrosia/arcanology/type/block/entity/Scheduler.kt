package nichrosia.arcanology.type.block.entity

interface Scheduler {
    var schedule: MutableList<Task>

    fun tick() {
        schedule.forEach { task ->
            if (task.ticksUntilRun == 0) {
                task.task()

                task.completed = true
            } else {
                task.ticksUntilRun--
            }
        }

        schedule = schedule.filter { !it.completed }.toMutableList()
    }

    fun schedule(ticksUntilRun: Int, task: () -> Unit) {
        schedule.add(Task(ticksUntilRun, task))
    }

    data class Task(var ticksUntilRun: Int, val task: () -> Unit) {
        var completed = false
    }
}