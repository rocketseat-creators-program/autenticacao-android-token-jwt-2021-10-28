const userService = require('../services/user')

const findOne = async ctx => {
  ctx.body = await userService.findOne(ctx.params.id)
}

const getAllUsers = async ctx => {
  ctx.body = await userService.getUsers()
}

const createUser = async ctx => {
  ctx.body = await userService.createUser(ctx.request.body)
}

module.exports = {
  findOne,
  getAllUsers,
  createUser,
}
